package com.example.onlinevotingsystem.service;

import com.example.onlinevotingsystem.dto.ElectionRequest;
import com.example.onlinevotingsystem.entity.Election;
import com.example.onlinevotingsystem.enums.ElectionStatus;
import com.example.onlinevotingsystem.repository.ElectionRepository;
import com.example.onlinevotingsystem.response.ApiResponse;
import com.example.onlinevotingsystem.response.MetaResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class ElectionService {

    @Autowired
    private ElectionRepository electionRepository;

    private static final ZoneId IST =
            ZoneId.of("Asia/Kolkata");

    public ApiResponse<Election> createElection(
            ElectionRequest request) {

        if (request == null) {
            throw new IllegalArgumentException(
                    "Election request is required");
        }

        if (request.getName() == null ||
                request.getName().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Election name is required");
        }

        if (request.getStartDate() == null) {

            throw new IllegalArgumentException(
                    "Election start date is required");
        }

        if (request.getEndDate() == null) {

            throw new IllegalArgumentException(
                    "Election end date is required");
        }

        int currentYear =
                LocalDate.now(IST).getYear();

        if (request.getStartDate().getYear() != currentYear) {

            throw new IllegalArgumentException(
                    "Election start date must be in the current year");
        }

        if (request.getEndDate().getYear() != currentYear) {

            throw new IllegalArgumentException(
                    "Election end date must be in the current year");
        }

        if (!request.getStartDate()
                .isBefore(request.getEndDate())) {

            throw new IllegalArgumentException(
                    "Election start date must be before end date");
        }

        if (request.getNumberOfSeats() <= 0) {

            throw new IllegalArgumentException(
                    "Number of seats must be greater than zero");
        }

        if (request.getNumberOfPollingBooths() <= 0) {

            throw new IllegalArgumentException(
                    "Number of polling booths must be greater than zero");
        }

        Election election = new Election();

        election.setName(
                request.getName().trim());

        election.setStartDate(
                request.getStartDate());

        election.setEndDate(
                request.getEndDate());

        election.setNumberOfSeats(
                request.getNumberOfSeats());

        election.setNumberOfPollingBooths(
                request.getNumberOfPollingBooths());

        LocalDateTime now =
                LocalDateTime.now(IST);

        // Set creation and update timestamps
        election.setCreatedAt(now);
        election.setUpdatedAt(now);

        election.setStatus(
                now.isBefore(
                        request.getStartDate())
                        ? ElectionStatus.SCHEDULED
                        : ElectionStatus.ACTIVE);

        Election savedElection =
                electionRepository.save(election);

        return new ApiResponse<>(
                true,
                savedElection,
                null,
                new MetaResponse(
                        now,
                        "Election created successfully"
                )
        );
    }

    @Scheduled(fixedRate = 60000)
    public void updateElectionStatus() {

        LocalDateTime now =
                LocalDateTime.now(IST);

        List<Election> elections =
                electionRepository.findAll();

        for (Election election : elections) {

            if (now.isBefore(
                    election.getStartDate())) {

                election.setStatus(
                        ElectionStatus.SCHEDULED);

            } else if (now.isBefore(
                    election.getEndDate())) {

                election.setStatus(
                        ElectionStatus.ACTIVE);

            } else {

                election.setStatus(
                        ElectionStatus.CLOSED);
            }

            // Update the modification timestamp
            election.setUpdatedAt(now);

            electionRepository.save(election);
        }
    }

    public ApiResponse<List<Election>> getAllElections(
            String search,
            int page,
            int size) {

        if (page < 0) {
            throw new IllegalArgumentException("Page number cannot be negative");
        }

        if (size <= 0) {
            throw new IllegalArgumentException("Page size must be greater than zero");
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<Election> electionPage;

        if (search == null || search.trim().isEmpty()) {
            electionPage = electionRepository.findAll(pageable);
        } else {
            electionPage = electionRepository.findByNameContainingIgnoreCase(
                    search.trim(), pageable);
        }

        LocalDateTime now = LocalDateTime.now(IST);

        if (electionPage.getContent().isEmpty()) {
            return new ApiResponse<>(
                    false,
                    null,
                    "No elections found",
                    new MetaResponse(now, "No elections found")
            );
        }

        return new ApiResponse<>(
                true,
                electionPage.getContent(),
                null,
                new MetaResponse(now, "Elections fetched successfully")
        );
    }
}
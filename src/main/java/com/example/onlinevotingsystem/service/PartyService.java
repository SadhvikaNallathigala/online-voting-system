package com.example.onlinevotingsystem.service;

import com.example.onlinevotingsystem.dto.PartyRequest;
import com.example.onlinevotingsystem.entity.Party;
import com.example.onlinevotingsystem.enums.PartyEnums.PartyStatus;
import com.example.onlinevotingsystem.repository.PartyRepository;
import com.example.onlinevotingsystem.response.ApiResponse;
import com.example.onlinevotingsystem.response.MetaResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PartyService {

    @Autowired
    private PartyRepository partyRepository;


    // CREATE PARTY
    public ApiResponse<Party> createParty(
            PartyRequest request) {

        if (request.getPartyName() == null ||
                request.getPartyName().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Party name is required");
        }

        if (request.getPartySymbol() == null) {

            throw new IllegalArgumentException(
                    "Party symbol is required. Please select an available party symbol from the enum");
        }

        if (request.getFounderName() == null ||
                request.getFounderName().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Founder name is required");
        }

        if (request.getStatus() == null) {

            throw new IllegalArgumentException(
                    "Party status is required. Please select ACTIVE or INACTIVE");
        }

        if (partyRepository.existsByPartyNameIgnoreCase(
                request.getPartyName().trim())) {

            throw new IllegalArgumentException(
                    "Party name already exists. Please check the available party names before creating a new party");
        }

        if (partyRepository.existsByPartySymbol(
                request.getPartySymbol())) {

            throw new IllegalArgumentException(
                    "Party symbol is already taken. Please select an available party symbol from the enum");
        }

        Party party = new Party();

        party.setPartyName(
                request.getPartyName().trim());

        party.setPartySymbol(
                request.getPartySymbol());

        party.setFounderName(
                request.getFounderName().trim());

        // Use the status provided by the user
        party.setStatus(
                request.getStatus());

        LocalDateTime now =
                LocalDateTime.now();

        party.setRegisteredDate(now);
        party.setCreatedAt(now);
        party.setUpdatedAt(now);

        Party savedParty =
                partyRepository.save(party);

        return new ApiResponse<>(
                true,
                savedParty,
                null,
                new MetaResponse(
                        now,
                        "Party created successfully"
                )
        );
    }


    // GET EXISTING PARTIES
    public ApiResponse<List<Party>> getExistingParties(
            int page,
            int size) {

        if (page < 0) {

            throw new IllegalArgumentException(
                    "Page number cannot be negative");
        }

        if (size <= 0) {

            throw new IllegalArgumentException(
                    "Page size must be greater than zero");
        }

        // Maximum 10 parties per page
        if (size > 10) {
            size = 10;
        }

        Pageable pageable =
                PageRequest.of(page, size);

        Page<Party> partyPage =
                partyRepository.findAll(pageable);

        return new ApiResponse<>(
                true,
                partyPage.getContent(),
                null,
                new MetaResponse(
                        LocalDateTime.now(),
                        "Existing parties retrieved successfully"
                )
        );
    }
}
package com.example.onlinevotingsystem.service;

import com.example.onlinevotingsystem.dto.VoterRequest;
import com.example.onlinevotingsystem.entity.Voter;
import com.example.onlinevotingsystem.repository.VoterRepository;
import com.example.onlinevotingsystem.response.ApiResponse;
import com.example.onlinevotingsystem.response.MetaResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;

@Service
public class VoterService {

    @Autowired
    private VoterRepository voterRepository;

    private static final ZoneId IST =
            ZoneId.of("Asia/Kolkata");

    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private final SecureRandom random =
            new SecureRandom();

    // REGISTER VOTER
    public ApiResponse<Voter> registerVoter(
            VoterRequest request) {

        if (request.getFullName() == null ||
                request.getFullName().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Full name is required");
        }

        if (request.getFatherName() == null ||
                request.getFatherName().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Father name is required");
        }

        if (request.getAadharNumber() == null ||
                !request.getAadharNumber().matches("\\d{12}")) {

            throw new IllegalArgumentException(
                    "Aadhar number must contain exactly 12 digits");
        }

        if (voterRepository.existsByAadharNumber(
                request.getAadharNumber())) {

            throw new IllegalArgumentException(
                    "Aadhar number already exists");
        }

        if (request.getAddress() == null ||
                request.getAddress().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Address is required");
        }

        if (request.getDateOfBirth() == null) {

            throw new IllegalArgumentException(
                    "Date of birth is required");
        }

        LocalDate today =
                LocalDate.now(IST);

        if (request.getDateOfBirth().isAfter(today)) {

            throw new IllegalArgumentException(
                    "Date of birth cannot be in the future");
        }

        int age = Period.between(
                request.getDateOfBirth(),
                today).getYears();

        if (age < 18) {

            throw new IllegalArgumentException(
                    "Voter must be at least 18 years old");
        }

        if (request.getGender() == null) {

            throw new IllegalArgumentException(
                    "Gender is required");
        }

        if (request.getMobileNumber() == null ||
                !request.getMobileNumber().matches("\\d{10}")) {

            throw new IllegalArgumentException(
                    "Mobile number must contain exactly 10 digits");
        }

        if (voterRepository.existsByMobileNumber(
                request.getMobileNumber())) {

            throw new IllegalArgumentException(
                    "Mobile number already exists");
        }

        if (request.getEmail() == null ||
                !request.getEmail().matches(
                        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            throw new IllegalArgumentException(
                    "Valid email is required");
        }

        if (voterRepository.existsByEmailIgnoreCase(
                request.getEmail().trim())) {

            throw new IllegalArgumentException(
                    "Email already exists");
        }

        if (request.getVoterStatus() == null) {

            throw new IllegalArgumentException(
                    "Voter status is required");
        }

        Voter voter = new Voter();

        String voterCode;

        do {
            voterCode = generateVoterCode();
        } while (voterRepository.existsByVoterCode(voterCode));

        voter.setVoterCode(voterCode);
        voter.setFullName(request.getFullName().trim());
        voter.setFatherName(request.getFatherName().trim());
        voter.setAadharNumber(request.getAadharNumber());
        voter.setAddress(request.getAddress().trim());
        voter.setDateOfBirth(request.getDateOfBirth());
        voter.setGender(request.getGender());
        voter.setMobileNumber(request.getMobileNumber());
        voter.setEmail(request.getEmail().trim());
        voter.setVoterStatus(request.getVoterStatus());

        LocalDateTime now =
                LocalDateTime.now(IST);

        voter.setRegisteredDate(now);
        voter.setCreatedAt(now);
        voter.setUpdatedAt(now);

        Voter savedVoter =
                voterRepository.save(voter);

        return new ApiResponse<>(
                true,
                savedVoter,
                null,
                new MetaResponse(
                        now,
                        "Voter registered successfully"
                )
        );
    }

    // GENERATE UNIQUE 10-DIGIT ALPHANUMERIC VOTER ID
    private String generateVoterCode() {

        StringBuilder code =
                new StringBuilder(10);

        for (int i = 0; i < 10; i++) {

            code.append(
                    CHARACTERS.charAt(
                            random.nextInt(
                                    CHARACTERS.length())));
        }

        return code.toString();
    }

    // GET / SEARCH VOTERS
    public ApiResponse<List<Voter>> getVoters(
            String search,
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

        if (size > 10) {
            size = 10;
        }

        Pageable pageable =
                PageRequest.of(page, size);

        Page<Voter> voterPage;

        if (search == null ||
                search.trim().isEmpty()) {

            voterPage =
                    voterRepository.findAll(pageable);

        } else {

            voterPage =
                    voterRepository.searchVoters(
                            search.trim(),
                            pageable);

            if (voterPage.isEmpty()) {

                throw new IllegalArgumentException(
                        "No voter found with the given search value");
            }
        }

        if (voterPage.isEmpty()) {

            throw new IllegalArgumentException(
                    "No voters found");
        }

        return new ApiResponse<>(
                true,
                voterPage.getContent(),
                null,
                new MetaResponse(
                        LocalDateTime.now(IST),
                        "Voters retrieved successfully"
                )
        );
    }
}
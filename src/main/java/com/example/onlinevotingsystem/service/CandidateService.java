package com.example.onlinevotingsystem.service;

import com.example.onlinevotingsystem.dto.CandidateRequest;
import com.example.onlinevotingsystem.entity.Candidate;
import com.example.onlinevotingsystem.entity.Election;
import com.example.onlinevotingsystem.entity.Party;
import com.example.onlinevotingsystem.enums.NominationEnums.CandidateStatus;
import com.example.onlinevotingsystem.enums.NominationEnums.PartySymbol;
import com.example.onlinevotingsystem.enums.PartyEnums.PartyStatus;
import com.example.onlinevotingsystem.repository.CandidateRepository;
import com.example.onlinevotingsystem.repository.ElectionRepository;
import com.example.onlinevotingsystem.repository.PartyRepository;
import com.example.onlinevotingsystem.response.ApiResponse;
import com.example.onlinevotingsystem.response.MetaResponse;
import com.example.onlinevotingsystem.enums.NominationEnums.CandidatePosition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private PartyRepository partyRepository;

    private static final ZoneId IST =
            ZoneId.of("Asia/Kolkata");


    public ApiResponse<Candidate> addCandidate(
            CandidateRequest request) {

        // Check election
        Election election = electionRepository
                .findById(request.getElectionId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Election not found"));


        // Check candidate name
        if (request.getCandidateName() == null ||
                request.getCandidateName().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Candidate name is required");
        }


        // Check nationality
        if (request.getNationality() == null) {

            throw new IllegalArgumentException(
                    "Nationality is required");
        }


        // Check Aadhar number
        if (request.getAadharNumber() == null ||
                request.getAadharNumber().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Aadhar number is required");
        }


        // Check age
        // Check age based on position
        if (request.getAge() <= 0) {

            throw new IllegalArgumentException(
                    "Candidate age must be greater than zero");
        }

        if (request.getPosition() == CandidatePosition.MLA &&
                request.getAge() < 25) {

            throw new IllegalArgumentException(
                    "Candidate must be at least 25 years old for MLA position");
        }

        if (request.getPosition() == CandidatePosition.MLC &&
                request.getAge() < 30) {

            throw new IllegalArgumentException(
                    "Candidate must be at least 30 years old for MLC position");
        }


        // Check position
        if (request.getPosition() == null) {

            throw new IllegalArgumentException(
                    "Position is required");
        }


        // Check Party ID
        if (request.getPartyId() == null) {

            throw new IllegalArgumentException(
                    "Party ID is required");
        }


        /*
         * Find party using Party ID
         * Only ACTIVE parties are allowed
         */
        Party party = partyRepository
                .findByPartyIdAndStatus(
                        request.getPartyId(),
                        PartyStatus.ACTIVE)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid party ID or party is inactive. Please select an ACTIVE party"));


        // Check participation place
        if (request.getParticipationPlace() == null ||
                request.getParticipationPlace().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Participation place is required");
        }


        // Create candidate
        Candidate candidate = new Candidate();


        // Connect candidate with election
        candidate.setElectionId(
                election.getElectionId());


        // Candidate details
        candidate.setCandidateName(
                request.getCandidateName().trim());

        candidate.setNationality(
                request.getNationality());

        candidate.setAadharNumber(
                request.getAadharNumber().trim());

        candidate.setAge(
                request.getAge());

        candidate.setPosition(
                request.getPosition());

        candidate.setParticipationPlace(
                request.getParticipationPlace().trim());


        /*
         * Connect candidate with Party
         */
        candidate.setParty(party);


        /*
         * Get party details from Party entity
         * instead of taking party name and symbol
         * separately from the request.
         */
        candidate.setPartyName(
                party.getPartyName());

        candidate.setPartySymbol(
                PartySymbol.valueOf(
                        party.getPartySymbol().name()));


        // New candidate starts as ACTIVE
        candidate.setStatus(
                CandidateStatus.ACTIVE);


        // Current IST time
        LocalDateTime now =
                LocalDateTime.now(IST);

        candidate.setCreatedAt(now);
        candidate.setUpdatedAt(now);


        // Save candidate
        Candidate savedCandidate =
                candidateRepository.save(candidate);


        // Response
        return new ApiResponse<>(
                true,
                savedCandidate,
                null,
                new MetaResponse(
                        now,
                        "Candidate nomination submitted successfully"
                )
        );
    }

    public ApiResponse<List<Candidate>> getCandidates(
            UUID electionId,
            int page,
            int size) {

        if (electionId == null) {
            throw new IllegalArgumentException(
                    "Election ID is required");
        }

        if (!electionRepository.existsById(electionId)) {
            throw new IllegalArgumentException(
                    "Election not found");
        }

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

        Page<Candidate> candidatePage =
                candidateRepository.findByElectionIdAndStatus(
                        electionId,
                        CandidateStatus.ACTIVE,
                        pageable);

        if (candidatePage.isEmpty()) {
            throw new IllegalArgumentException(
                    "No active candidates found for this election");
        }

        return new ApiResponse<>(
                true,
                candidatePage.getContent(),
                null,
                new MetaResponse(
                        LocalDateTime.now(IST),
                        "Candidates retrieved successfully"
                )
        );
    }
}
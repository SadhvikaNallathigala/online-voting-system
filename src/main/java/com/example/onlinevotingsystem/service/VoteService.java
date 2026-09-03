package com.example.onlinevotingsystem.service;

import com.example.onlinevotingsystem.dto.VoteRequest;
import com.example.onlinevotingsystem.entity.Candidate;
import com.example.onlinevotingsystem.entity.Vote;
import com.example.onlinevotingsystem.entity.Voter;
import com.example.onlinevotingsystem.enums.NominationEnums.CandidateStatus;
import com.example.onlinevotingsystem.enums.NominationEnums.VoteStatus;
import com.example.onlinevotingsystem.repository.CandidateRepository;
import com.example.onlinevotingsystem.repository.VoteRepository;
import com.example.onlinevotingsystem.repository.VoterRepository;
import com.example.onlinevotingsystem.response.ApiResponse;
import com.example.onlinevotingsystem.response.MetaResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    private static final ZoneId IST =
            ZoneId.of("Asia/Kolkata");

    public ApiResponse<Vote> castVote(VoteRequest request) {

        // 1. Validate request
        if (request == null) {
            throw new IllegalArgumentException(
                    "Vote request is required");
        }

        // 2. Validate election ID
        if (request.getElectionId() == null) {
            throw new IllegalArgumentException(
                    "Election ID is required");
        }

        // 3. Validate voter code
        if (request.getVoterCode() == null ||
                request.getVoterCode().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Voter code is required");
        }

        // 4. Validate candidate ID
        if (request.getCandidateId() == null) {
            throw new IllegalArgumentException(
                    "Candidate ID is required");
        }

        // 5. Validate candidate name
        if (request.getCandidateName() == null ||
                request.getCandidateName().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Candidate name is required");
        }

        // 6. Validate party name
        if (request.getPartyName() == null ||
                request.getPartyName().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Party name is required");
        }

        // 7. Find voter
        Voter voter = voterRepository
                .findByVoterCode(
                        request.getVoterCode().trim())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Voter code not found"));

        // 8. Check voter status
        if (voter.getVoterStatus() == null) {
            throw new IllegalArgumentException(
                    "Voter status is not available");
        }

        if (!voter.getVoterStatus().name()
                .equals("ACTIVE")) {

            throw new IllegalArgumentException(
                    "Voter is inactive and cannot cast a vote");
        }

        // 9. Check whether voter already voted
        //    IN THIS PARTICULAR ELECTION
        if (voteRepository.existsByElectionIdAndVoterId(
                request.getElectionId(),
                voter.getVoterCode())) {

            throw new IllegalArgumentException(
                    "Voter has already cast a vote in this election");
        }

        // 10. Find candidate
        UUID candidateId = request.getCandidateId();

        Candidate candidate = candidateRepository
                .findById(candidateId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Candidate not found"));

        // 11. Check candidate status
        if (candidate.getStatus() == null) {
            throw new IllegalArgumentException(
                    "Candidate status is not available");
        }

        if (candidate.getStatus() !=
                CandidateStatus.ACTIVE) {

            throw new IllegalArgumentException(
                    "Candidate is inactive and cannot receive a vote");
        }

        // 12. Check candidate name
        if (candidate.getCandidateName() == null) {
            throw new IllegalArgumentException(
                    "Candidate name is not available");
        }

        if (!candidate.getCandidateName()
                .equalsIgnoreCase(
                        request.getCandidateName().trim())) {

            throw new IllegalArgumentException(
                    "Candidate name does not match candidate ID");
        }

        // 13. Check party name
        if (candidate.getPartyName() == null) {
            throw new IllegalArgumentException(
                    "Party name is not available");
        }

        if (!candidate.getPartyName()
                .equalsIgnoreCase(
                        request.getPartyName().trim())) {

            throw new IllegalArgumentException(
                    "Party name does not match candidate ID");
        }

        // 14. Check voter and candidate place
        if (voter.getAddress() == null ||
                voter.getAddress().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Voter address is not available");
        }

        if (candidate.getParticipationPlace() == null ||
                candidate.getParticipationPlace().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Candidate participation place is not available");
        }

        if (!voter.getAddress().trim()
                .equalsIgnoreCase(
                        candidate.getParticipationPlace().trim())) {

            throw new IllegalArgumentException(
                    "Voter and candidate must belong to the same place");
        }

        // 15. Create vote
        Vote vote = new Vote();

        // Store the election in which this vote was cast
        vote.setElectionId(request.getElectionId());

        vote.setVoterId(
                voter.getVoterCode());

        vote.setCandidateId(
                candidate.getCandidateId());

        vote.setCandidateName(
                candidate.getCandidateName());

        vote.setPartyName(
                candidate.getPartyName());

        vote.setVoteStatus(
                VoteStatus.CAST);

        // 16. Set timestamps
        LocalDateTime now =
                LocalDateTime.now(IST);

        vote.setVotedAt(now);
        vote.setCreatedAt(now);
        vote.setUpdatedAt(now);

        // 17. Save vote
        Vote savedVote =
                voteRepository.save(vote);

        // 18. Return response
        return new ApiResponse<>(
                true,
                savedVote,
                null,
                new MetaResponse(
                        now,
                        "Vote cast successfully"
                )
        );
    }
}
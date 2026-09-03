package com.example.onlinevotingsystem.service;

import com.example.onlinevotingsystem.dto.CandidateResult;
import com.example.onlinevotingsystem.dto.ElectionResult;
import com.example.onlinevotingsystem.dto.RecountResult;
import com.example.onlinevotingsystem.entity.Candidate;
import com.example.onlinevotingsystem.entity.Election;
import com.example.onlinevotingsystem.enums.ElectionStatus;
import com.example.onlinevotingsystem.enums.NominationEnums.VoteStatus;
import com.example.onlinevotingsystem.repository.CandidateRepository;
import com.example.onlinevotingsystem.repository.ElectionRepository;
import com.example.onlinevotingsystem.repository.VoteRepository;
import com.example.onlinevotingsystem.response.ApiResponse;
import com.example.onlinevotingsystem.response.MetaResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class RecountService {

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private VoteRepository voteRepository;

    private static final ZoneId IST =
            ZoneId.of("Asia/Kolkata");

    public ApiResponse<RecountResult> recount(
            UUID electionId) {

        if (electionId == null) {
            throw new IllegalArgumentException(
                    "Election ID is required");
        }

        Election election = electionRepository
                .findById(electionId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Election not found"));

        if (election.getStatus() != ElectionStatus.CLOSED) {
            throw new IllegalArgumentException(
                    "Only closed elections can be recounted");
        }

        List<Candidate> candidates =
                candidateRepository
                        .findByElectionId(
                                electionId,
                                PageRequest.of(0, 100))
                        .getContent();

        if (candidates.isEmpty()) {
            throw new IllegalArgumentException(
                    "No candidates found for this election");
        }

        List<CandidateResult> results =
                new ArrayList<>();

        for (Candidate candidate : candidates) {

            long votes =
                    voteRepository.countVotesByCandidateId(
                            candidate.getCandidateId(),
                            VoteStatus.CAST);

            results.add(
                    CandidateResult.builder()
                            .candidateId(
                                    candidate.getCandidateId())
                            .candidateName(
                                    candidate.getCandidateName())
                            .partyName(
                                    candidate.getPartyName())
                            .partySymbol(
                                    candidate.getPartySymbol())
                            .nationality(
                                    candidate.getNationality())
                            .age(
                                    candidate.getAge())
                            .position(
                                    candidate.getPosition())
                            .participationPlace(
                                    candidate.getParticipationPlace())
                            .status(
                                    candidate.getStatus())
                            .votes(votes)
                            .build()
            );
        }

        results.sort(
                Comparator.comparingLong(
                                CandidateResult::getVotes)
                        .reversed());

        for (int i = 0; i < results.size(); i++) {
            results.get(i).setRank(i + 1);
        }

        long highestVotes =
                results.get(0).getVotes();

        List<CandidateResult> highestCandidates =
                results.stream()
                        .filter(result ->
                                result.getVotes() ==
                                        highestVotes)
                        .toList();

        CandidateResult winner = null;
        String resultStatus;
        String recountStatus;
        String message;

        if (highestCandidates.size() > 1) {

            resultStatus = "TIE";
            recountStatus = "RE_ELECTION_REQUIRED";

            message =
                    "Recount completed. Tie still exists. Re-election is required";

            election.setStatus(
                    ElectionStatus.RE_ELECTION_REQUIRED);

        } else {

            winner = highestCandidates.get(0);

            resultStatus = "WINNER_DECLARED";
            recountStatus = "COMPLETED";

            message =
                    winner.getCandidateName()
                            + " from "
                            + winner.getPartyName()
                            + " is the winner after recount";

            election.setStatus(
                    ElectionStatus.COMPLETED);
        }

        electionRepository.save(election);

        ElectionResult electionResult =
                ElectionResult.builder()
                        .electionName(
                                election.getName())
                        .resultStatus(resultStatus)
                        .winnerMessage(message)
                        .winner(winner)
                        .candidates(results)
                        .build();

        RecountResult recountResult =
                RecountResult.builder()
                        .result(electionResult)
                        .recountStatus(recountStatus)
                        .message(message)
                        .build();

        LocalDateTime now =
                LocalDateTime.now(IST);

        return new ApiResponse<>(
                true,
                recountResult,
                null,
                new MetaResponse(
                        now,
                        "Election recount completed successfully")
        );
    }
}
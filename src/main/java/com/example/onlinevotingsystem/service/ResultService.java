package com.example.onlinevotingsystem.service;

import com.example.onlinevotingsystem.dto.CandidateResult;
import com.example.onlinevotingsystem.dto.ElectionResult;
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
public class ResultService {

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private VoteRepository voteRepository;

    private static final ZoneId IST =
            ZoneId.of("Asia/Kolkata");

    public ApiResponse<ElectionResult> getResults(
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
                    "Election is not closed. Results cannot be declared");
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

            long voteCount =
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
                            .votes(
                                    voteCount)
                            .build()
            );
        }

        results.sort(
                Comparator.comparingLong(
                                CandidateResult::getVotes)
                        .reversed());

        int rank = 1;

        for (CandidateResult result : results) {
            result.setRank(rank++);
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
        String winnerMessage;

        if (highestCandidates.size() > 1) {

            resultStatus = "TIE";

            winnerMessage =
                    "Tie detected. Recount is required";

        } else {

            resultStatus = "WINNER_DECLARED";

            winner = highestCandidates.get(0);

            winnerMessage =
                    winner.getCandidateName()
                            + " from "
                            + winner.getPartyName()
                            + " is the winner";
        }

        ElectionResult electionResult =
                ElectionResult.builder()
                        .electionName(
                                election.getName())
                        .resultStatus(
                                resultStatus)
                        .winnerMessage(
                                winnerMessage)
                        .winner(
                                winner)
                        .candidates(
                                results)
                        .build();

        LocalDateTime now =
                LocalDateTime.now(IST);

        return new ApiResponse<>(
                true,
                electionResult,
                null,
                new MetaResponse(
                        now,
                        "Election results retrieved successfully")
        );
    }
}
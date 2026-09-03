package com.example.onlinevotingsystem.repository;

import com.example.onlinevotingsystem.entity.Vote;
import com.example.onlinevotingsystem.enums.NominationEnums.VoteStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface VoteRepository extends JpaRepository<Vote, UUID> {

    boolean existsByElectionIdAndVoterId(
            UUID electionId,
            String voterId);

    @Query("""
            SELECT COUNT(v)
            FROM Vote v
            WHERE v.candidateId = :candidateId
            AND v.voteStatus = :voteStatus
            """)
    long countVotesByCandidateId(
            @Param("candidateId") UUID candidateId,
            @Param("voteStatus") VoteStatus voteStatus);
}
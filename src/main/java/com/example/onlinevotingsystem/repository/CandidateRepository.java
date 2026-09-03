package com.example.onlinevotingsystem.repository;

import com.example.onlinevotingsystem.entity.Candidate;
import com.example.onlinevotingsystem.enums.NominationEnums.CandidateStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CandidateRepository
        extends JpaRepository<Candidate, UUID> {

    Page<Candidate> findByElectionIdAndStatus(
            UUID electionId,
            CandidateStatus status,
            Pageable pageable);

    Page<Candidate> findByElectionId(
            UUID electionId,
            Pageable pageable);
}
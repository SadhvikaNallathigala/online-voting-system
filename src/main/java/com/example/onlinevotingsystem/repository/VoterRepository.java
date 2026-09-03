package com.example.onlinevotingsystem.repository;

import com.example.onlinevotingsystem.entity.Voter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import java.util.UUID;

public interface VoterRepository extends JpaRepository<Voter, UUID> {

    boolean existsByVoterCode(String voterCode);

    boolean existsByAadharNumber(String aadharNumber);

    boolean existsByMobileNumber(String mobileNumber);

    boolean existsByEmailIgnoreCase(String email);

    @Query("""
            SELECT v FROM Voter v
            WHERE LOWER(v.voterCode) LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(v.fullName) LIKE LOWER(CONCAT('%', :search, '%'))
               OR v.aadharNumber LIKE CONCAT('%', :search, '%')
               OR v.mobileNumber LIKE CONCAT('%', :search, '%')
               OR LOWER(v.email) LIKE LOWER(CONCAT('%', :search, '%'))
            """)
    Page<Voter> searchVoters(
            @Param("search") String search,
            Pageable pageable);

    Optional<Voter> findByVoterCode(String voterCode);
}
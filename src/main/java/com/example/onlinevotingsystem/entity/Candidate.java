package com.example.onlinevotingsystem.entity;

import com.example.onlinevotingsystem.enums.NominationEnums.CandidatePosition;
import com.example.onlinevotingsystem.enums.NominationEnums.CandidateStatus;
import com.example.onlinevotingsystem.enums.NominationEnums.Nationality;
import com.example.onlinevotingsystem.enums.NominationEnums.PartySymbol;

import jakarta.persistence.*;
import lombok.Data;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "candidates")
public class Candidate {

    @Id
    @GeneratedValue
    @Column(name = "candidate_id")
    private UUID candidateId;

    @Column(name = "election_id", nullable = false)
    private UUID electionId;

    @Column(name = "candidate_name", nullable = false)
    private String candidateName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Nationality nationality;

    @Column(name = "aadhar_number", nullable = false, unique = true)
    private String aadharNumber;

    @Column(nullable = false)
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CandidatePosition position;

    @Column(name = "party_name", nullable = false)
    private String partyName;

    @Enumerated(EnumType.STRING)
    @Column(name = "party_symbol", nullable = false)
    private PartySymbol partySymbol;

    @Column(name = "participation_place", nullable = false)
    private String participationPlace;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CandidateStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "party_id", nullable = false)
    private Party party;
}
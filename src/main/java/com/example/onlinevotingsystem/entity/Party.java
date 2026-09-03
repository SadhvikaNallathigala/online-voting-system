package com.example.onlinevotingsystem.entity;

import com.example.onlinevotingsystem.enums.PartyEnums.PartyStatus;
import com.example.onlinevotingsystem.enums.PartyEnums.PartySymbol;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "parties")
public class Party {

    @Id
    @GeneratedValue
    @Column(name = "party_id")
    private UUID partyId;

    @Column(name = "party_name", nullable = false, unique = true)
    private String partyName;

    @Enumerated(EnumType.STRING)
    @Column(name = "party_symbol", nullable = false, unique = true)
    private PartySymbol partySymbol;

    @Column(name = "founder_name", nullable = false)
    private String founderName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PartyStatus status;

    @Column(name = "registered_date", nullable = false)
    private LocalDateTime registeredDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
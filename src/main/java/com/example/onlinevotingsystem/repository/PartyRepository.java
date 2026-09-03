package com.example.onlinevotingsystem.repository;

import com.example.onlinevotingsystem.entity.Party;
import com.example.onlinevotingsystem.enums.PartyEnums.PartyStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import com.example.onlinevotingsystem.enums.PartyEnums;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

import java.util.Optional;
import java.util.UUID;

public interface PartyRepository
        extends JpaRepository<Party, UUID> {

    boolean existsByPartyNameIgnoreCase(String partyName);

    boolean existsByPartySymbol(
            com.example.onlinevotingsystem.enums.PartyEnums.PartySymbol partySymbol);

    Page<Party> findAll(Pageable pageable);

    Page<Party> findByStatus(
            PartyStatus status,
            Pageable pageable);

    Optional<Party> findByPartyIdAndStatus(
            UUID partyId,
            PartyStatus status);
}
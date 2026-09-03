package com.example.onlinevotingsystem.repository;

import com.example.onlinevotingsystem.entity.Election;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ElectionRepository
        extends JpaRepository<Election, UUID> {

    boolean existsByNameIgnoreCase(String name);

    Page<Election> findByNameContainingIgnoreCase(
            String name,
            Pageable pageable);
}
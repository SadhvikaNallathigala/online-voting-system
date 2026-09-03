package com.example.onlinevotingsystem.entity;

import com.example.onlinevotingsystem.enums.VoterEnums.Gender;
import com.example.onlinevotingsystem.enums.VoterEnums.VoterStatus;

import jakarta.persistence.*;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "voters")
public class Voter {

    @Id
    @GeneratedValue
    @Column(name = "voter_id")
    private UUID voterId;

    @Column(name = "voter_code", nullable = false, unique = true, length = 10)
    private String voterCode;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "father_name", nullable = false)
    private String fatherName;

    @Column(name = "aadhar_number", nullable = false, unique = true)
    private String aadharNumber;

    @Column(nullable = false)
    private String address;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(name = "mobile_number", nullable = false, unique = true)
    private String mobileNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "voter_status", nullable = false)
    private VoterStatus voterStatus;

    @Column(name = "registered_date", nullable = false)
    private LocalDateTime registeredDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
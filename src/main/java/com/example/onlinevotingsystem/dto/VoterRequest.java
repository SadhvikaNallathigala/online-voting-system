package com.example.onlinevotingsystem.dto;

import com.example.onlinevotingsystem.enums.VoterEnums.Gender;
import com.example.onlinevotingsystem.enums.VoterEnums.VoterStatus;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VoterRequest {

    private String fullName;

    private String fatherName;

    private String aadharNumber;

    private String address;

    private LocalDate dateOfBirth;

    private Gender gender;

    private String mobileNumber;

    private String email;

    private VoterStatus voterStatus;
}
package com.example.onlinevotingsystem.dto;

import com.example.onlinevotingsystem.enums.NominationEnums.CandidatePosition;
import com.example.onlinevotingsystem.enums.NominationEnums.Nationality;

import lombok.Data;

import java.util.UUID;

@Data
public class CandidateRequest {

    private UUID electionId;

    private String candidateName;

    private Nationality nationality;

    private String aadharNumber;

    private Integer age;

    private CandidatePosition position;

    private UUID partyId;

    private String participationPlace;
}
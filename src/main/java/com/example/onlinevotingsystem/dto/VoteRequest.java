package com.example.onlinevotingsystem.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class VoteRequest {

    private UUID electionId;

    private String voterCode;

    private UUID candidateId;

    private String candidateName;

    private String partyName;
}
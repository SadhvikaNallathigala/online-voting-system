package com.example.onlinevotingsystem.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ElectionResult {

    private String electionName;
    private String resultStatus;
    private String winnerMessage;
    private CandidateResult winner;
    private List<CandidateResult> candidates;
}
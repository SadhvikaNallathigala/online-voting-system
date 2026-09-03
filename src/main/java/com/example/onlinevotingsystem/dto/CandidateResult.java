package com.example.onlinevotingsystem.dto;

import com.example.onlinevotingsystem.enums.NominationEnums.CandidatePosition;
import com.example.onlinevotingsystem.enums.NominationEnums.CandidateStatus;
import com.example.onlinevotingsystem.enums.NominationEnums.Nationality;
import com.example.onlinevotingsystem.enums.NominationEnums.PartySymbol;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CandidateResult {

    private UUID candidateId;
    private String candidateName;
    private String partyName;
    private PartySymbol partySymbol;
    private Nationality nationality;
    private int age;
    private CandidatePosition position;
    private String participationPlace;
    private CandidateStatus status;
    private long votes;
    private int rank;
}
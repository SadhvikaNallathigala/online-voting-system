package com.example.onlinevotingsystem.dto;

import com.example.onlinevotingsystem.enums.PartyEnums.PartyStatus;
import com.example.onlinevotingsystem.enums.PartyEnums.PartySymbol;

import lombok.Data;

@Data
public class PartyRequest {

    private String partyName;

    private PartySymbol partySymbol;

    private String founderName;

    private PartyStatus status;
}
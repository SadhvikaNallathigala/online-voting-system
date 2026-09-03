package com.example.onlinevotingsystem.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecountResult {

    private ElectionResult result;
    private String recountStatus;
    private String message;
}
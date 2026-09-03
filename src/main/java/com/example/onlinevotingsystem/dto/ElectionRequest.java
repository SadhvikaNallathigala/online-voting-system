package com.example.onlinevotingsystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ElectionRequest {

    private String name;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private int numberOfSeats;

    private int numberOfPollingBooths;
}
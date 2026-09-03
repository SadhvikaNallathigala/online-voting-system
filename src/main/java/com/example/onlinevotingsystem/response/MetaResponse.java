package com.example.onlinevotingsystem.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MetaResponse {

    private LocalDateTime timestamp;
    private String message;

    public MetaResponse(
            LocalDateTime timestamp,
            String message) {

        this.timestamp = timestamp;
        this.message = message;
    }
}
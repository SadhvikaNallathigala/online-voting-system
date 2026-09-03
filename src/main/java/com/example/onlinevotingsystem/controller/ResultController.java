package com.example.onlinevotingsystem.controller;

import com.example.onlinevotingsystem.dto.ElectionResult;
import com.example.onlinevotingsystem.response.ApiResponse;
import com.example.onlinevotingsystem.service.ResultService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/elections")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @GetMapping("/{electionId}/results")
    public ApiResponse<ElectionResult> getResults(
            @PathVariable UUID electionId) {

        return resultService.getResults(electionId);
    }
}
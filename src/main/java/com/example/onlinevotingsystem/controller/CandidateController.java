package com.example.onlinevotingsystem.controller;

import com.example.onlinevotingsystem.dto.CandidateRequest;
import com.example.onlinevotingsystem.entity.Candidate;
import com.example.onlinevotingsystem.response.ApiResponse;
import com.example.onlinevotingsystem.service.CandidateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping
    public ApiResponse<Candidate> addCandidate(
            @RequestBody CandidateRequest request) {

        return candidateService.addCandidate(request);
    }

    @GetMapping
    public ApiResponse<List<Candidate>> getCandidates(
            @RequestParam UUID electionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return candidateService.getCandidates(
                electionId,
                page,
                size);
    }
}
package com.example.onlinevotingsystem.controller;

import com.example.onlinevotingsystem.dto.VoterRequest;
import com.example.onlinevotingsystem.entity.Voter;
import com.example.onlinevotingsystem.response.ApiResponse;
import com.example.onlinevotingsystem.service.VoterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voters")
public class VoterController {

    @Autowired
    private VoterService voterService;

    @PostMapping
    public ApiResponse<Voter> registerVoter(
            @RequestBody VoterRequest request) {

        return voterService.registerVoter(request);
    }

    @GetMapping
    public ApiResponse<List<Voter>> getVoters(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return voterService.getVoters(
                search,
                page,
                size);
    }
}
package com.example.onlinevotingsystem.controller;

import com.example.onlinevotingsystem.dto.VoteRequest;
import com.example.onlinevotingsystem.entity.Vote;
import com.example.onlinevotingsystem.response.ApiResponse;
import com.example.onlinevotingsystem.service.VoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @PostMapping
    public ApiResponse<Vote> castVote(
            @RequestBody VoteRequest request) {

        return voteService.castVote(request);
    }
}
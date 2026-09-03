package com.example.onlinevotingsystem.controller;

import com.example.onlinevotingsystem.dto.ElectionRequest;
import com.example.onlinevotingsystem.entity.Election;
import com.example.onlinevotingsystem.response.ApiResponse;
import com.example.onlinevotingsystem.service.ElectionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/elections")
public class ElectionController {

    @Autowired
    private ElectionService electionService;

    @PostMapping
    public ApiResponse<Election> createElection(
            @RequestBody ElectionRequest request) {

        return electionService.createElection(request);
    }

    @GetMapping
    public ApiResponse<List<Election>> getAllElections(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return electionService.getAllElections(
                search,
                page,
                size);
    }
}
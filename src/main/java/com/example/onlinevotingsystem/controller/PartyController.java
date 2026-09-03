package com.example.onlinevotingsystem.controller;

import com.example.onlinevotingsystem.dto.PartyRequest;
import com.example.onlinevotingsystem.entity.Party;
import com.example.onlinevotingsystem.response.ApiResponse;
import com.example.onlinevotingsystem.service.PartyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parties")
public class PartyController {

    @Autowired
    private PartyService partyService;

    @PostMapping
    public ApiResponse<Party> createParty(
            @RequestBody PartyRequest request) {

        return partyService.createParty(request);
    }

    @GetMapping("/existing")
    public ApiResponse<List<Party>> getExistingParties(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return partyService.getExistingParties(page, size);
    }
}
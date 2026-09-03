package com.example.onlinevotingsystem.controller;

import com.example.onlinevotingsystem.dto.RecountResult;
import com.example.onlinevotingsystem.response.ApiResponse;
import com.example.onlinevotingsystem.service.RecountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/elections")
public class RecountController {

    @Autowired
    private RecountService recountService;

    @PostMapping("/{electionId}/recount")
    public ApiResponse<RecountResult> recount(
            @PathVariable UUID electionId) {

        return recountService.recount(electionId);
    }
}
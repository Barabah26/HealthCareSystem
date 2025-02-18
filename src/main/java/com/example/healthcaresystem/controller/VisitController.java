package com.example.healthcaresystem.controller;

import com.example.healthcaresystem.dto.VisitRequestDto;
import com.example.healthcaresystem.service.VisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {
    private final VisitService visitService;

    @PostMapping
    public ResponseEntity<String> createVisit(@Valid @RequestBody VisitRequestDto request) {
        boolean success = visitService.createVisit(request);
        if (!success) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Doctor already has a visit at this time.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Visit created successfully.");
    }
}

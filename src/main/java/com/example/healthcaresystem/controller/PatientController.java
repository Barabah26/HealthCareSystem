package com.example.healthcaresystem.controller;

import com.example.healthcaresystem.dto.PatientResponseDto;
import com.example.healthcaresystem.service.PatientService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/patients")
@AllArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public Page<PatientResponseDto> getPatients(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false) List<Long> doctorIds) {
        return patientService.getPatients(page, size, search, doctorIds);
    }
}


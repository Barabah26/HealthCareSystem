package com.example.healthcaresystem.service;

import com.example.healthcaresystem.dto.PatientResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PatientService {
    Page<PatientResponseDto> getPatients(int page, int size, String search, List<Long> doctorIds);
}

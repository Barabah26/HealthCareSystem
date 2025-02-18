package com.example.healthcaresystem.service.impl;

import com.example.healthcaresystem.dto.DoctorDto;
import com.example.healthcaresystem.dto.PatientResponseDto;
import com.example.healthcaresystem.dto.VisitDto;
import com.example.healthcaresystem.entity.Patient;
import com.example.healthcaresystem.repository.DoctorRepository;
import com.example.healthcaresystem.repository.PatientRepository;
import com.example.healthcaresystem.repository.VisitRepository;
import com.example.healthcaresystem.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final VisitRepository visitRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public Page<PatientResponseDto> getPatients(int page, int size, String search, List<Long> doctorIds) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Patient> patients = patientRepository.searchPatients(search, pageable);

        List<PatientResponseDto> response = patients.stream().map(patient -> {
            List<VisitDto> visits = visitRepository.findLastVisits(patient.getId()).stream().map(visit -> {
                DoctorDto doctorDto = new DoctorDto();
                doctorDto.setFirstName(visit.getDoctor().getFirstName());
                doctorDto.setLastName(visit.getDoctor().getLastName());
                doctorDto.setTotalPatients(doctorRepository.countUniquePatients(visit.getDoctor().getId()));

                VisitDto visitDto = new VisitDto();
                visitDto.setStart(visit.getStartDateTime().toString());
                visitDto.setEnd(visit.getEndDateTime().toString());
                visitDto.setDoctor(doctorDto);
                return visitDto;
            }).collect(Collectors.toList());

            PatientResponseDto dto = new PatientResponseDto();
            dto.setFirstName(patient.getFirstName());
            dto.setLastName(patient.getLastName());
            dto.setLastVisits(visits);
            return dto;
        }).collect(Collectors.toList());

        return new PageImpl<>(response, pageable, patients.getTotalElements());
    }
}

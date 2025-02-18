package com.example.healthcaresystem.service.impl;

import com.example.healthcaresystem.dto.VisitRequestDto;
import com.example.healthcaresystem.entity.Doctor;
import com.example.healthcaresystem.entity.Patient;
import com.example.healthcaresystem.entity.Visit;
import com.example.healthcaresystem.exception.EntityNotFoundException;
import com.example.healthcaresystem.repository.DoctorRepository;
import com.example.healthcaresystem.repository.PatientRepository;
import com.example.healthcaresystem.repository.VisitRepository;
import com.example.healthcaresystem.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {
    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public boolean createVisit(VisitRequestDto request) {
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime start = LocalDateTime.parse(request.getStart(), formatter);
        LocalDateTime end = LocalDateTime.parse(request.getEnd(), formatter);

        if (!visitRepository.findOverlappingVisits(doctor.getId(), start, end).isEmpty()) {
            return false;
        }

        Visit visit = new Visit(null, start, end, patient, doctor);
        visitRepository.save(visit);
        return true;
    }
}
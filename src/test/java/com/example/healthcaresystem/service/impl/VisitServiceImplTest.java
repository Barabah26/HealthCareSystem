package com.example.healthcaresystem.service.impl;

import com.example.healthcaresystem.dto.VisitRequestDto;
import com.example.healthcaresystem.entity.Doctor;
import com.example.healthcaresystem.entity.Patient;
import com.example.healthcaresystem.entity.Visit;
import com.example.healthcaresystem.exception.EntityNotFoundException;
import com.example.healthcaresystem.repository.DoctorRepository;
import com.example.healthcaresystem.repository.PatientRepository;
import com.example.healthcaresystem.repository.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitServiceImplTest {

    @Mock
    private VisitRepository visitRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private VisitServiceImpl visitService;

    private VisitRequestDto visitRequestDto;
    private Doctor doctor;
    private Patient patient;

    @BeforeEach
    void setUp() {
        doctor = new Doctor();
        doctor.setId(1L);

        patient = new Patient();
        patient.setId(1L);

        visitRequestDto = new VisitRequestDto();
        visitRequestDto.setDoctorId(1L);
        visitRequestDto.setPatientId(1L);
        visitRequestDto.setStart("2025-02-19 10:00");
        visitRequestDto.setEnd("2025-02-19 11:00");
    }

    @Test
    void testCreateVisit_Success() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(visitRepository.findOverlappingVisits(1L, LocalDateTime.parse("2025-02-19 10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2025-02-19 11:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))).thenReturn(java.util.Collections.emptyList());

        boolean result = visitService.createVisit(visitRequestDto);

        assertTrue(result);
        verify(visitRepository, times(1)).save(any(Visit.class));
    }

    @Test
    void testCreateVisit_DoctorNotFound() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> visitService.createVisit(visitRequestDto));
    }

    @Test
    void testCreateVisit_PatientNotFound() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> visitService.createVisit(visitRequestDto));
    }

    @Test
    void testCreateVisit_OverlappingVisit() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(visitRepository.findOverlappingVisits(1L, LocalDateTime.parse("2025-02-19 10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2025-02-19 11:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))).thenReturn(java.util.Collections.singletonList(new Visit()));

        boolean result = visitService.createVisit(visitRequestDto);

        assertFalse(result);
        verify(visitRepository, never()).save(any(Visit.class));
    }
}

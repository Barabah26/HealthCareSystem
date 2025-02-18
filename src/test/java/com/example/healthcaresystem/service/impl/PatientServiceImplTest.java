package com.example.healthcaresystem.service.impl;

import com.example.healthcaresystem.dto.DoctorDto;
import com.example.healthcaresystem.dto.PatientResponseDto;
import com.example.healthcaresystem.dto.VisitDto;
import com.example.healthcaresystem.entity.Doctor;
import com.example.healthcaresystem.entity.Patient;
import com.example.healthcaresystem.entity.Visit;
import com.example.healthcaresystem.repository.DoctorRepository;
import com.example.healthcaresystem.repository.PatientRepository;
import com.example.healthcaresystem.repository.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private VisitRepository visitRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    private Patient patient;
    private Visit visit;
    private Doctor doctor;

    @BeforeEach
    void setUp() {
        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setFirstName("Stepan");
        doctor.setLastName("Popov");

        patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("Anna");
        patient.setLastName("Johnson");

        visit = new Visit();
        visit.setDoctor(doctor);
        visit.setPatient(patient);
        visit.setStartDateTime(LocalDateTime.of(2025, 2, 20, 10, 0));
        visit.setEndDateTime(LocalDateTime.of(2025, 2, 20, 10, 30));
    }

    @Test
    void testGetPatients() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<Patient> patientPage = new PageImpl<>(List.of(patient), pageable, 1);

        when(patientRepository.searchPatients(anyString(), any(Pageable.class))).thenReturn(patientPage);
        when(visitRepository.findLastVisits(anyLong())).thenReturn(List.of(visit));
        when(doctorRepository.countUniquePatients(anyLong())).thenReturn(5);

        Page<PatientResponseDto> result = patientService.getPatients(0, 2, "", Collections.emptyList());

        assertEquals(1, result.getTotalElements());
        assertEquals("Anna", result.getContent().get(0).getFirstName());
        assertEquals("Johnson", result.getContent().get(0).getLastName());
        assertEquals(1, result.getContent().get(0).getLastVisits().size());
        VisitDto visitDto = result.getContent().get(0).getLastVisits().get(0);
        assertEquals("2025-02-20T10:00", visitDto.getStart());
        assertEquals("2025-02-20T10:30", visitDto.getEnd());
        DoctorDto doctorDto = visitDto.getDoctor();
        assertEquals("Stepan", doctorDto.getFirstName());
        assertEquals("Popov", doctorDto.getLastName());
        assertEquals(5, doctorDto.getTotalPatients());
    }
}
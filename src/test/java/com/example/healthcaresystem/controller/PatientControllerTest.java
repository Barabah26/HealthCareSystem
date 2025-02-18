package com.example.healthcaresystem.controller;

import com.example.healthcaresystem.dto.PatientResponseDto;
import com.example.healthcaresystem.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Test
    void getPatients() throws Exception {
        PatientResponseDto patientResponseDto = new PatientResponseDto();
        patientResponseDto.setFirstName("John");
        patientResponseDto.setLastName("Doe");
        patientResponseDto.setLastVisits(Collections.emptyList());

        Pageable pageable = PageRequest.of(0, 10);
        Page<PatientResponseDto> patientPage = new PageImpl<>(Collections.singletonList(patientResponseDto), pageable, 1);

        when(patientService.getPatients(0, 10, "", null)).thenReturn(patientPage);

        mockMvc.perform(get("/patients")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("John"))
                .andExpect(jsonPath("$.content[0].lastName").value("Doe"));

        verify(patientService, times(1)).getPatients(0, 10, "", null);
    }
}


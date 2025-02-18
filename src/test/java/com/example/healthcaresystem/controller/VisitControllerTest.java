package com.example.healthcaresystem.controller;

import com.example.healthcaresystem.dto.VisitRequestDto;
import com.example.healthcaresystem.service.VisitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VisitController.class)
class VisitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VisitService visitService;

    @Test
    void createVisit() throws Exception {
        when(visitService.createVisit(any(VisitRequestDto.class))).thenReturn(true);

        mockMvc.perform(post("/visits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "start": "2025-02-20 10:00",
                                "end": "2025-02-20 11:00",
                                "patientId": 1,
                                "doctorId": 2
                            }
                            """))
                .andExpect(status().isCreated())
                .andExpect(content().string("Visit created successfully."));
    }


    @Test
    void createVisit_Conflict_DoctorBusy() throws Exception {
        VisitRequestDto requestDto = new VisitRequestDto();
        requestDto.setStart("2025-02-20 10:00");
        requestDto.setEnd("2025-02-20 11:00");
        requestDto.setPatientId(1L);
        requestDto.setDoctorId(2L);

        when(visitService.createVisit(requestDto)).thenReturn(false);

        mockMvc.perform(post("/visits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "start": "2025-02-20 10:00",
                                    "end": "2025-02-20 11:00",
                                    "patientId": 1,
                                    "doctorId": 2
                                }
                                """))
                .andExpect(status().isConflict())
                .andExpect(content().string("Doctor already has a visit at this time."));
    }
}

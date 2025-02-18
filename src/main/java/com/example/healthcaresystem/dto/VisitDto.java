package com.example.healthcaresystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisitDto {
    private String start;
    private String end;
    private DoctorDto doctor;
}


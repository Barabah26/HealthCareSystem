package com.example.healthcaresystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PatientResponseDto {
    private String firstName;
    private String lastName;
    private List<VisitDto> lastVisits;
}


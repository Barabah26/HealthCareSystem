package com.example.healthcaresystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorDto {
    private String firstName;
    private String lastName;
    private int totalPatients;
}


package com.example.healthcaresystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisitRequestDto {
    @NotNull
    private String start;

    @NotNull
    private String end;

    @NotNull
    private Long patientId;

    @NotNull
    private Long doctorId;
}

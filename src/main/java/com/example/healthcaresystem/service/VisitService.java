package com.example.healthcaresystem.service;

import com.example.healthcaresystem.dto.VisitRequestDto;

public interface VisitService {
    boolean createVisit(VisitRequestDto request);
}

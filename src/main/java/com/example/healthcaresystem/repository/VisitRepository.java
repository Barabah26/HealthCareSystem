package com.example.healthcaresystem.repository;

import com.example.healthcaresystem.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    @Query("SELECT v FROM Visit v WHERE v.doctor.id = :doctorId AND ((v.startDateTime BETWEEN :start AND :end) OR (v.endDateTime BETWEEN :start AND :end))")
    List<Visit> findOverlappingVisits(@Param("doctorId") Long doctorId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT v FROM Visit v WHERE v.patient.id = :patientId ORDER BY v.startDateTime DESC")
    List<Visit> findLastVisits(@Param("patientId") Long patientId);
}

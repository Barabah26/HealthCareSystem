package com.example.healthcaresystem.repository;

import com.example.healthcaresystem.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("SELECT COUNT(DISTINCT v.patient) FROM Visit v WHERE v.doctor.id = :doctorId")
    int countUniquePatients(@Param("doctorId") Long doctorId);
}


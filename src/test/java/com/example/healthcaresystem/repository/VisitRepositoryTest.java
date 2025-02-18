package com.example.healthcaresystem.repository;

import com.example.healthcaresystem.entity.Doctor;
import com.example.healthcaresystem.entity.Patient;
import com.example.healthcaresystem.entity.Visit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("local")
class VisitRepositoryTest {

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void findLastVisits_ShouldReturnCorrectOrder() {
        Doctor doctor = new Doctor(null, "John", "Doe", "UTC");
        doctor = doctorRepository.save(doctor);

        Patient patient = new Patient(null, "Alice", "Smith");
        patient = patientRepository.save(patient);

        LocalDateTime fixedStartTime1 = LocalDateTime.now().minusDays(1);
        LocalDateTime fixedEndTime1 = fixedStartTime1.plusHours(1);
        LocalDateTime fixedStartTime2 = LocalDateTime.now().minusHours(2);
        LocalDateTime fixedEndTime2 = fixedStartTime2.plusHours(1);

        Visit visit1 = new Visit(null, fixedStartTime1, fixedEndTime1, patient, doctor);
        Visit visit2 = new Visit(null, fixedStartTime2, fixedEndTime2, patient, doctor);

        visitRepository.saveAll(List.of(visit1, visit2));
        entityManager.flush();

        List<Visit> lastVisits = visitRepository.findLastVisits(patient.getId());

        assertThat(lastVisits).hasSize(2);
        assertThat(lastVisits.get(0).getStartDateTime()).isAfter(lastVisits.get(1).getStartDateTime());
    }
}

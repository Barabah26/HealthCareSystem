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
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private PatientRepository patientRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void countUniquePatients_ShouldReturnCorrectCount() {
        Doctor doctor = new Doctor(null, "John", "Doe", "UTC");
        doctor = doctorRepository.save(doctor);

        Patient patient1 = new Patient(null, "Alice", "Smith");
        Patient patient2 = new Patient(null, "Bob", "Johnson");
        patientRepository.saveAll(List.of(patient1, patient2));

        visitRepository.save(new Visit(null, LocalDateTime.now(), LocalDateTime.now().plusHours(1), patient1, doctor));
        visitRepository.save(new Visit(null, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(1), patient2, doctor));
        visitRepository.save(new Visit(null, LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(2).plusHours(1), patient1, doctor));

        entityManager.flush();

        int uniquePatientsCount = doctorRepository.countUniquePatients(doctor.getId());

        assertThat(uniquePatientsCount).isEqualTo(2);
    }
}

package com.example.healthcaresystem.repository;

import com.example.healthcaresystem.entity.Patient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("local")
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void searchPatients() {
        Patient patient1 = new Patient(null, "Alice", "Smith");
        Patient patient2 = new Patient(null, "Bob", "Johnson");
        Patient patient3 = new Patient(null, "Charlie", "Brown");

        patientRepository.saveAll(List.of(patient1, patient2, patient3));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Patient> result = patientRepository.searchPatients("alice", pageable);
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).contains(patient1);
        result = patientRepository.searchPatients("johnson", pageable);
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).contains(patient2);
        result = patientRepository.searchPatients("notfound", pageable);
        assertThat(result.getTotalElements()).isEqualTo(0);
    }
}

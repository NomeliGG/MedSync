package com.medtech.appointment.repository;

import com.medtech.appointment.model.Patient;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findPatientById(Long patientId);
}

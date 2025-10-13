package com.medtech.appointment.repository;

import com.medtech.appointment.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findDoctorById(Long doctorId);
}

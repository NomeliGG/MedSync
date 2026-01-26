package com.medtech.appointment.repository;

import com.medtech.appointment.model.Doctor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findDoctorById(Long doctorId);
}

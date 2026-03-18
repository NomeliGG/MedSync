package com.medtech.appointment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "doctor")
@Getter
@Setter
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 128)
    private String firstName;

    @Column(name = "last_name", length = 128)
    private String lastName;

    @Column(name = "date_of_birth", columnDefinition = "DATE")
    private LocalDate dateOfBirth;

    @Column(name = "sex", columnDefinition = "bpchar")
    private String sex;

    @Column(name = "additional_info", columnDefinition = "TEXT")
    private String additionalInfo;

    @OneToMany(mappedBy = "doctor")
    private List<DoctorSchedule> schedules;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;
}

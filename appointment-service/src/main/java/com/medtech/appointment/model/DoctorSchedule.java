package com.medtech.appointment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "doctor_schedule")
@Getter
@Setter
public class DoctorSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(name = "day_of_week", columnDefinition = "DATE")
    private LocalDate dayOfWeek;

    @Column(name = "work_interval", columnDefinition = "TSRANGE")
    private String workInterval;

    @Column(name = "break_interval", columnDefinition = "TSRANGE")
    private String breakInterval;

    @Column(name = "created", columnDefinition = "TIMESTAMP")
    private LocalDateTime created;

    @Column(name = "updated", columnDefinition = "TIMESTAMP")
    private LocalDateTime updated;
}

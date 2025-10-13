package com.medtech.appointment.model;

import jakarta.persistence.*;
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

    @Column(name = "day_of_week")
    private java.sql.Date dayOfWeek;

    @Column(name = "work_interval", columnDefinition = "TIMERANGE")
    private String workInterval;

    @Column(name = "break_interval", columnDefinition = "TIMERANGE")
    private String breakInterval;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "updated")
    private LocalDateTime updated;
}

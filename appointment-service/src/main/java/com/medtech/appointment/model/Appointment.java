package com.medtech.appointment.model;

import com.medtech.appointment.enums.Status;
import com.medtech.appointment.enums.VisitType;
import com.medtech.platform.util.time.UtcClock;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "appointment")
@Getter
@Setter
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "start_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime startTime;

    @Column(name = "end_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 32)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "visit_type", length = 32)
    private VisitType visitType;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @Column(name = "created", columnDefinition = "TIMESTAMP")
    private LocalDateTime created;

    @Column(name = "updated", columnDefinition = "TIMESTAMP")
    private LocalDateTime updated;

    @PrePersist
    public void prePersist() {
        created = UtcClock.nowLocal();
        updated = created;
    }

    @PreUpdate
    public void preUpdate() {
        updated = UtcClock.nowLocal();
    }
}

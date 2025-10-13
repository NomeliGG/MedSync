package com.medtech.appointment.inout;

import com.medtech.appointment.enums.VisitType;

import java.time.LocalDateTime;

public record AppointmentBookIn(
        Long doctorId,
        Long patientId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        VisitType visitType,
        String reason
) {}
package com.medtech.appointment.inout;

import com.medtech.appointment.enums.Status;
import com.medtech.appointment.enums.VisitType;
import java.time.LocalDateTime;

public record AppointmentViewOut(
        Long appointmentId,
        Long doctorId,
        Long patientId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Status status,
        VisitType visitType,
        String reason
) {}

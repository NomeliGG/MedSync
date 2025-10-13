package com.medtech.appointment.inout;

import com.medtech.appointment.enums.Status;
import java.time.LocalDateTime;

public record AppointmentRescheduleOut(
        Long appointmentId,
        LocalDateTime newStartTime,
        LocalDateTime newEndTime,
        Status status
) {}
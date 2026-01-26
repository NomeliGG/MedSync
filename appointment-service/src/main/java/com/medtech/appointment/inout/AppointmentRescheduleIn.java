package com.medtech.appointment.inout;

import java.time.LocalDateTime;

public record AppointmentRescheduleIn(
        Long appointmentId,
        LocalDateTime newStartTime,
        LocalDateTime newEndTime
) {}

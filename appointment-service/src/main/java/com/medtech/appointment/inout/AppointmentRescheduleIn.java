package com.medtech.appointment.inout;

import java.time.LocalDateTime;

public record AppointmentRescheduleIn(
        LocalDateTime newStartTime,
        LocalDateTime newEndTime
) {}

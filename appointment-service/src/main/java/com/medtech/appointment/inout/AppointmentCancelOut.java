package com.medtech.appointment.inout;

import com.medtech.appointment.enums.Status;

public record AppointmentCancelOut(
        Long appointmentId,
        Status status,
        String message
) {}
package com.medtech.appointment.inout;

public record AppointmentCancelIn(
        Long appointmentId,
        Long cancelledById
) {}

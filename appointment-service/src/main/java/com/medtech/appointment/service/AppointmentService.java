package com.medtech.appointment.service;


import com.medtech.appointment.inout.*;

import java.util.List;

public interface AppointmentService {

    AppointmentViewOut book(AppointmentBookIn in);

    AppointmentCancelOut cancelByPatient(AppointmentCancelIn in);

    AppointmentCancelOut cancelByDoctor(AppointmentCancelIn in);

    AppointmentRescheduleOut reschedule(AppointmentRescheduleIn in);

    List<AppointmentViewOut> getAppointmentsByPatient(Long patientId);

    List<AppointmentViewOut> getAppointmentsByDoctor(Long doctorId);
}

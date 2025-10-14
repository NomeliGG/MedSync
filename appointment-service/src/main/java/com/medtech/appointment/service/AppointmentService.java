package com.medtech.appointment.service;

import com.medtech.appointment.inout.AppointmentBookIn;
import com.medtech.appointment.inout.AppointmentCancelIn;
import com.medtech.appointment.inout.AppointmentCancelOut;
import com.medtech.appointment.inout.AppointmentRescheduleIn;
import com.medtech.appointment.inout.AppointmentRescheduleOut;
import com.medtech.appointment.inout.AppointmentViewOut;
import java.util.List;

public interface AppointmentService {

    AppointmentViewOut book(AppointmentBookIn in);

    AppointmentCancelOut cancelByPatient(AppointmentCancelIn in);

    AppointmentCancelOut cancelByDoctor(AppointmentCancelIn in);

    AppointmentRescheduleOut reschedule(AppointmentRescheduleIn in);

    List<AppointmentViewOut> getAppointmentsByPatient(Long patientId);

    List<AppointmentViewOut> getAppointmentsByDoctor(Long doctorId);
}

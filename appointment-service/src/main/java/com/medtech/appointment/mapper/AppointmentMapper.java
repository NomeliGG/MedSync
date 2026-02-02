package com.medtech.appointment.mapper;

import com.medtech.appointment.inout.AppointmentBookIn;
import com.medtech.appointment.inout.AppointmentRescheduleOut;
import com.medtech.appointment.inout.AppointmentViewOut;
import com.medtech.appointment.model.Appointment;
import com.medtech.appointment.model.Doctor;
import com.medtech.appointment.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(target = "id", ignore = true)
    Appointment toModel(AppointmentBookIn in, Doctor doctor, Patient patient);

    @Mapping(target = "appointmentId", source = "id")
    AppointmentViewOut toViewOut(Appointment appointment);

    @Mapping(target = "appointmentId", source = "id")
    AppointmentRescheduleOut toRescheduleOut(Appointment appointment);
}

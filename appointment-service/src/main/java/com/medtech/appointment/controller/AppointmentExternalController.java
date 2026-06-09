package com.medtech.appointment.controller;

import com.medtech.appointment.inout.AppointmentBookIn;
import com.medtech.appointment.inout.AppointmentCancelIn;
import com.medtech.appointment.inout.AppointmentCancelOut;
import com.medtech.appointment.inout.AppointmentRescheduleIn;
import com.medtech.appointment.inout.AppointmentRescheduleOut;
import com.medtech.appointment.inout.AppointmentViewOut;
import com.medtech.appointment.service.AppointmentService;
import com.medtech.platform.web.security.session.DefaultUserSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/external/appointments")
@RequiredArgsConstructor
public class AppointmentExternalController {

    private final AppointmentService appointmentService;

    @PostMapping
    public AppointmentViewOut book(@RequestBody AppointmentBookIn in) {
        return appointmentService.book(in);
    }

    @PostMapping("/cancel/patient")
    public AppointmentCancelOut cancelByPatient(
            @RequestBody AppointmentCancelIn in
    ) {
        return appointmentService.cancelByPatient(in);
    }

    @PostMapping("/cancel/doctor")
    public AppointmentCancelOut cancelByDoctor(
            @RequestBody AppointmentCancelIn in
    ) {
        return appointmentService.cancelByDoctor(in);
    }

    @PostMapping("/{id}/reschedule")
    public AppointmentRescheduleOut reschedule(
            @PathVariable("id") Long id,
            @RequestBody AppointmentRescheduleIn in
    ) {
        return appointmentService.reschedule(id, in);
    }

    @GetMapping("/patient/{patientId}")
    public List<AppointmentViewOut> getByPatient(
            DefaultUserSession session,
            @PathVariable("patientId") Long patientId
    ) {
        return appointmentService.getAppointmentsByPatient(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<AppointmentViewOut> getByDoctor(@PathVariable("doctorId") Long doctorId) {
        return appointmentService.getAppointmentsByDoctor(doctorId);
    }
}

package com.medtech.appointment.service;

import com.medtech.appointment.enums.Status;
import com.medtech.appointment.exception.TimeSlotAlreadyBookedException;
import com.medtech.appointment.inout.AppointmentBookIn;
import com.medtech.appointment.inout.AppointmentCancelIn;
import com.medtech.appointment.inout.AppointmentCancelOut;
import com.medtech.appointment.inout.AppointmentRescheduleIn;
import com.medtech.appointment.inout.AppointmentRescheduleOut;
import com.medtech.appointment.inout.AppointmentViewOut;
import com.medtech.appointment.mapper.AppointmentMapper;
import com.medtech.appointment.model.Appointment;
import com.medtech.appointment.model.Doctor;
import com.medtech.appointment.model.Patient;
import com.medtech.appointment.repository.AppointmentRepository;
import com.medtech.appointment.repository.DoctorRepository;
import com.medtech.appointment.repository.PatientRepository;
import com.medtech.platform.exception.DataNotFoundException;
import com.medtech.platform.exception.WrongDataException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    @Transactional
    public AppointmentViewOut book(AppointmentBookIn in) {
        // TODO integrate with Doctor Working Hours to validate slot availability

        boolean exists = appointmentRepository.existsByDoctorIdAndTimeOverlap(
                in.doctorId(), in.startTime(), in.endTime());
        if (exists) {
            throw new IllegalArgumentException("Time slot already booked.");
        }

        Doctor doctor = doctorRepository.findDoctorById(in.doctorId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Doctor with id: %s was not found".formatted(in.doctorId())
                ));

        Patient patient = patientRepository.findPatientById(in.patientId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Patient with id: %s was not found".formatted(in.patientId())
                ));
        Appointment appointment = appointmentMapper.toModel(in, doctor, patient);

        Appointment saved = appointmentRepository.save(appointment);

        return appointmentMapper.toViewOut(saved);
    }

    @Override
    @Transactional
    public AppointmentCancelOut cancelByPatient(AppointmentCancelIn in) {
        Appointment appointment = findById(in.appointmentId());

        if (!appointment.getPatient().getId().equals(in.cancelledById())) {
            throw new IllegalArgumentException("Patient not authorized to cancel this appointment.");
        }

        appointment.setStatus(Status.CANCELLED_BY_PATIENT);
        appointmentRepository.save(appointment);

        return new AppointmentCancelOut(appointment.getId(), appointment.getStatus(), "Cancelled by patient");
    }

    @Override
    @Transactional
    public AppointmentCancelOut cancelByDoctor(AppointmentCancelIn in) {
        Appointment appointment = findById(in.appointmentId());

        if (!appointment.getDoctor().getId().equals(in.cancelledById())) {
            throw new WrongDataException("Doctor not authorized to cancel others appointments");
        }

        appointment.setStatus(Status.CANCELLED_BY_DOCTOR);
        appointmentRepository.save(appointment);

        return new AppointmentCancelOut(appointment.getId(), appointment.getStatus(), "Cancelled by doctor");
    }

    @Override
    @Transactional
    public AppointmentRescheduleOut reschedule(AppointmentRescheduleIn in) {
        Appointment appointment = findById(in.appointmentId());

        // TODO integrate with Doctor Working Hours to validate new slot availability

        boolean exists = appointmentRepository.existsByDoctorIdAndTimeOverlap(
                appointment.getDoctor().getId(), in.newStartTime(), in.newEndTime());
        if (exists) {
            throw new TimeSlotAlreadyBookedException("New time slot already booked.");
        }

        appointment.setStartTime(in.newStartTime());
        appointment.setEndTime(in.newEndTime());
        appointment.setStatus(Status.BOOKED);
        appointmentRepository.save(appointment);

        return appointmentMapper.toRescheduleOut(appointment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentViewOut> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findAllByPatientId(patientId)
                .stream()
                .map(appointmentMapper::toViewOut)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentViewOut> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findAllByDoctorId(doctorId)
                .stream()
                .map(appointmentMapper::toViewOut)
                .collect(Collectors.toList());
    }

    private Appointment findById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));
    }

}

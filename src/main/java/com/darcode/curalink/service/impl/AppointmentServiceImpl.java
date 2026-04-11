package com.darcode.curalink.service.impl;

import com.darcode.curalink.dto.appointment.AppointmentResponse;
import com.darcode.curalink.dto.appointment.CompleteAppointmentRequest;
import com.darcode.curalink.dto.appointment.ScheduleAppointmentRequest;
import com.darcode.curalink.dto.appointment.ScheduleAppointmentResponse;
import com.darcode.curalink.enums.AppointmetStatus;
import com.darcode.curalink.enums.Role;
import com.darcode.curalink.exception.ConflictException;
import com.darcode.curalink.exception.ResourceNotFoundException;
import com.darcode.curalink.mapper.AppointmentMapper;
import com.darcode.curalink.model.Appointment;
import com.darcode.curalink.model.MedicalRecord;
import com.darcode.curalink.model.TimeSlot;
import com.darcode.curalink.model.User;
import com.darcode.curalink.repository.AppointmentRepository;
import com.darcode.curalink.repository.TimeSlotRepository;
import com.darcode.curalink.repository.UserRepository;
import com.darcode.curalink.service.AppointmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public ScheduleAppointmentResponse schedule(ScheduleAppointmentRequest scheduleAppointmentRequest) {
        User doctor = userRepository.findByRoleAndId(Role.DOCTOR, scheduleAppointmentRequest.doctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", scheduleAppointmentRequest.doctorId()));
        User patient = userRepository.findByRoleAndId(Role.PATIENT, scheduleAppointmentRequest.doctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", scheduleAppointmentRequest.patientId()));
        TimeSlot timeSlot = timeSlotRepository.findByIdAndDoctorId(
                scheduleAppointmentRequest.timeSlotId(),
                scheduleAppointmentRequest.doctorId()
        ).orElseThrow(() -> new ResourceNotFoundException(
                "TimeSlot with id " + scheduleAppointmentRequest.timeSlotId() + " not found for doctor " + scheduleAppointmentRequest.doctorId()
        ));

        if (timeSlot.getIsAvailable()) {
            Appointment appointment = new Appointment();
            appointment.setReason(appointment.getReason());
            appointment.setDoctor(doctor);
            appointment.setSheduledAt(LocalDateTime.now());
            appointment.setStatus(AppointmetStatus.PENDING);
            appointment.setPatient(patient);
            appointment.setTimeSlot(timeSlot);

            return appointmentMapper.toScheduleAppointmentResponse(
                    appointmentRepository.save(appointment)
            );
        } else {
            throw new ConflictException("This time slot is already taken");
        }
    }

    @Override
    public Page<AppointmentResponse> findUserAppointments(String userEmail, Pageable pageable) {
        Page<Appointment> appointments = appointmentRepository.findAllByPatientEmailOrDoctorEmail(userEmail, userEmail, pageable);

        return appointments.map(appointmentMapper::toAppointmentResponse);
    }

    @Override
    @Transactional
    public void cancelAppointment(Integer appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(
                () -> new ResourceNotFoundException("Appointment", appointmentId)
        );

        TimeSlot timeSlot = timeSlotRepository.findByAppointmentId(appointmentId);
        timeSlot.setIsAvailable(true);
        timeSlotRepository.save(timeSlot);

        appointment.setStatus(AppointmetStatus.CANCELED);
        appointmentRepository.save(appointment);
    }

    @Override
    public void complete(Integer appointmentId, CompleteAppointmentRequest completeRequest) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(
                () -> new ResourceNotFoundException("Appointment", appointmentId)
        );

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setDiagnosis(completeRequest.diagnosis());
        medicalRecord.setPrescription(completeRequest.prescription());
        medicalRecord.setNotes(completeRequest.notes());
        medicalRecord.setAppointment(appointment);

        appointment.setMedicalRecord(medicalRecord);

        appointmentRepository.save(appointment);
    }

    @Override
    public Page<AppointmentResponse> findAllAppointments(Pageable pageable) {
        Page<Appointment> appointments = appointmentRepository.findAll(pageable);

        return appointments.map(appointmentMapper::toAppointmentResponse);
    }
}

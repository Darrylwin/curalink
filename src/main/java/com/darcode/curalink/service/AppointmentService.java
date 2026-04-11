package com.darcode.curalink.service;

import com.darcode.curalink.dto.appointment.AppointmentResponse;
import com.darcode.curalink.dto.appointment.CompleteAppointmentRequest;
import com.darcode.curalink.dto.appointment.ScheduleAppointmentRequest;
import com.darcode.curalink.dto.appointment.ScheduleAppointmentResponse;
import com.darcode.curalink.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {

    ScheduleAppointmentResponse schedule(ScheduleAppointmentRequest scheduleAppointmentRequest);

    Page<AppointmentResponse> findUserAppointments(String userEmail, Pageable pageable);

    void cancelAppointment(Integer appointmentId);

    void complete(Integer appointmentId, CompleteAppointmentRequest completeRequest);

    Page<AppointmentResponse> findAllAppointments(Pageable pageable);

    List<Appointment> findAllAppointmentsBetween(LocalDateTime start, LocalDateTime end);
}

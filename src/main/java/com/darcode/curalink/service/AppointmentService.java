package com.darcode.curalink.service;

import com.darcode.curalink.dto.appointment.AppointmentResponse;
import com.darcode.curalink.dto.appointment.CompleteAppointmentRequest;
import com.darcode.curalink.dto.appointment.ScheduleAppointmentRequest;
import com.darcode.curalink.dto.appointment.ScheduleAppointmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {

    ScheduleAppointmentResponse schedule(ScheduleAppointmentRequest scheduleAppointmentRequest);

    Page<AppointmentResponse> findUserAppointments(String userEmail, Pageable pageable);

    void cancelAppointment(Integer appointmentId);

    public void complete(Integer appointmentId, CompleteAppointmentRequest completeRequest);

    Page<AppointmentResponse> findAllAppointments(Pageable pageable);
}

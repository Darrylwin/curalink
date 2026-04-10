package com.darcode.curalink.service.impl;

import com.darcode.curalink.dto.appointment.ScheduleAppointmentRequest;
import com.darcode.curalink.dto.appointment.ScheduleAppointmentResponse;
import com.darcode.curalink.repository.AppointmentRepository;
import com.darcode.curalink.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public ScheduleAppointmentResponse schedule(ScheduleAppointmentRequest scheduleAppointmentRequest) {
        // TODO
    }
}

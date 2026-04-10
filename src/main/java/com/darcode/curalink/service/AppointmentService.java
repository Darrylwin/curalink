package com.darcode.curalink.service;

import com.darcode.curalink.dto.appointment.ScheduleAppointmentRequest;
import com.darcode.curalink.dto.appointment.ScheduleAppointmentResponse;

public interface AppointmentService {
    ScheduleAppointmentResponse schedule(ScheduleAppointmentRequest scheduleAppointmentRequest);
}

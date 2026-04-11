package com.darcode.curalink.dto.appointment;

import com.darcode.curalink.enums.AppointmetStatus;

import java.time.Duration;
import java.time.LocalDateTime;

public record AppointmentResponse(
        Integer id,
        LocalDateTime scheduledAt,
        AppointmetStatus status,
        String reason,
        ScheduleAppointmentResponse.DoctorInfo doctor,
        ScheduleAppointmentResponse.PatientInfo patient,
        TimeSlotInfo timeSlot

) {
    public record TimeSlotInfo(
            LocalDateTime startTime, LocalDateTime endTime,
            Boolean isAvailable, Duration duration
    ) {}
}

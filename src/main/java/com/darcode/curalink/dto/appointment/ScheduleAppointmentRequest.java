package com.darcode.curalink.dto.appointment;

import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;

public record ScheduleAppointmentRequest(
        @Null
        String reason,

        @Positive
        Integer doctorId,

        @Positive
        Integer patientId,

        @Positive
        Integer timeSlotId
) {
}

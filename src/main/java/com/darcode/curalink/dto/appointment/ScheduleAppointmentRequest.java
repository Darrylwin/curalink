package com.darcode.curalink.dto.appointment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ScheduleAppointmentRequest(
        @NotBlank(message = "Reason must not be blank")
        String reason,

        @NotNull(message = "Doctor ID is required")
        @Positive(message = "Doctor ID must be positive")
        Integer doctorId,

        @NotNull(message = "Patient ID is required")
        @Positive(message = "Patient ID must be positive")
        Integer patientId,

        @NotNull(message = "Time slot ID is required")
        @Positive(message = "Time slot ID must be positive")
        Integer timeSlotId
) {
}
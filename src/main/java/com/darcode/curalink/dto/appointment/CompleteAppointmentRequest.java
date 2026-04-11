package com.darcode.curalink.dto.appointment;

import jakarta.validation.constraints.NotBlank;

public record CompleteAppointmentRequest(
        @NotBlank(message = "The diagnosis is required")
        String diagnosis,

        @NotBlank(message = "A prescription is required")
        String prescription,

        String notes
) {
}

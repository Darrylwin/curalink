package com.darcode.curalink.dto.appointment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CompleteAppointmentRequest(
        @NotBlank
        String diagnosis,

        @NotBlank
        String prescription,

        String notes
) {
}

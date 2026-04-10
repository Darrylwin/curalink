package com.darcode.curalink.dto.doctors;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record DefineTimeSlotRequest(
        @Positive
        Integer doctorId,

        @FutureOrPresent
        @NotBlank
        LocalDateTime startTime,

        @Future
        @NotBlank
        LocalDateTime endTime
) {
}

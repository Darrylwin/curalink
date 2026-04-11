package com.darcode.curalink.dto.doctors;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record DefineTimeSlotRequest(
        @FutureOrPresent(message = "Start time must be now or in future")
        @NotNull(message = "Start time is required")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
        LocalDateTime startTime,

        @Future(message = "End time must be in future")
        @NotNull(message = "End time is required")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
        LocalDateTime endTime
) {
}

package com.darcode.curalink.dto.doctors;

import java.time.Duration;
import java.time.LocalDateTime;

public record DefineTimeSlotResponse(
        Integer doctorId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Duration duration,
        Boolean isAvailable
) {
}

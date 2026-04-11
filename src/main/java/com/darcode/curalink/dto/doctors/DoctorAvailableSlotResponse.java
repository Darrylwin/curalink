package com.darcode.curalink.dto.doctors;

import java.time.Duration;
import java.time.LocalDateTime;

public record DoctorAvailableSlotResponse(
        Integer timeSlotId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Duration duration
) {
}

package com.darcode.curalink.dto.doctors;

import java.time.Duration;
import java.time.LocalDateTime;

public record DoctorAvailableSlotResponse(
        LocalDateTime startTime,
        LocalDateTime endTime,
        Duration duration
) {
}

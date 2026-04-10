package com.darcode.curalink.mapper;

import com.darcode.curalink.dto.doctors.DoctorAvailableSlotResponse;
import com.darcode.curalink.dto.doctors.DoctorResponse;
import com.darcode.curalink.model.TimeSlot;
import com.darcode.curalink.model.User;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class DoctorMapper {
    public DoctorResponse toDoctorResponse(User doctor) {
        return new DoctorResponse(
                doctor.getId(),
                doctor.getFirstName(),
                doctor.getEmail(),
                String.valueOf(doctor.getRole()),
                doctor.getDoctorProfile().getSpeciality(),
                doctor.getDoctorProfile().getBio()
        );
    }

    public DoctorAvailableSlotResponse toAvailableSlotResponse(TimeSlot timeSlot) {
        return new DoctorAvailableSlotResponse(
                timeSlot.getStartTime(),
                timeSlot.getEndTime(),
                Duration.between(
                        timeSlot.getStartTime(),
                        timeSlot.getEndTime()
                )
        );
    }
}
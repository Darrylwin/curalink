package com.darcode.curalink.mapper;

import com.darcode.curalink.dto.doctors.DoctorResponse;
import com.darcode.curalink.model.User;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {
    public DoctorResponse toResponse(User doctor) {
        return new DoctorResponse(
                doctor.getId(),
                doctor.getFirstName(),
                doctor.getEmail(),
                String.valueOf(doctor.getRole()),
                doctor.getDoctorProfile().getSpeciality(),
                doctor.getDoctorProfile().getBio()
        );
    }
}
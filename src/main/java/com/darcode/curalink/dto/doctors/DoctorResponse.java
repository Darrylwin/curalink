package com.darcode.curalink.dto.doctors;

public record DoctorResponse(
        Integer id,
        String firstName,
        String email,
        String role,
        String speciality,
        String bio
) {
}

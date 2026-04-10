package com.darcode.curalink.dto.appointment;

import com.darcode.curalink.enums.AppointmetStatus;
import com.darcode.curalink.model.User;

import java.math.BigDecimal;

public record ScheduleAppointmentResponse(
        Integer id,
        AppointmetStatus status,
        String reason,
        DoctorInfo doctor,
        PatientInfo patient

) {
    public record DoctorInfo(Integer doctorId, String firstName, String email, BigDecimal consulttionFee) {}
    public record PatientInfo(Integer patientId, String firstName, String email) {}
}

package com.darcode.curalink.dto.medicalRecord;

public record MedicalRecordResponse(
        String diagnosis,
        String prescription,
        String notes,
        String appointmentReason
) {
}

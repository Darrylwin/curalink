package com.darcode.curalink.mapper;

import com.darcode.curalink.dto.medicalRecord.MedicalRecordResponse;
import com.darcode.curalink.model.MedicalRecord;
import org.springframework.stereotype.Component;

@Component
public class MedicalRecordMapper {
    public MedicalRecordResponse toMedicalRecordResponse(MedicalRecord medicalRecord) {
        return new MedicalRecordResponse(
                medicalRecord.getDiagnosis(),
                medicalRecord.getPrescription(),
                medicalRecord.getNotes(),
                medicalRecord.getAppointment().getReason()
        );
    }
}

package com.darcode.curalink.service;

import com.darcode.curalink.dto.medicalRecord.MedicalRecordResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MedicalRecordService {
    Page<MedicalRecordResponse> findUserRecords(String userEmail, Pageable pageable);

    public Page<MedicalRecordResponse> findAllByPatient(Integer patientId, Pageable pageable);
}

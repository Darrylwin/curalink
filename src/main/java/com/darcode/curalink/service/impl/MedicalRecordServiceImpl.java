package com.darcode.curalink.service.impl;

import com.darcode.curalink.dto.medicalRecord.MedicalRecordResponse;
import com.darcode.curalink.mapper.AppointmentMapper;
import com.darcode.curalink.mapper.MedicalRecordMapper;
import com.darcode.curalink.model.MedicalRecord;
import com.darcode.curalink.repository.MedicalRecordRepository;
import com.darcode.curalink.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicalRecordMapper medicalRecordMapper;

    @Override
    public Page<MedicalRecordResponse> findUserRecords(String userEmail, Pageable pageable) {
        log.debug("Searching medical records for user {}", userEmail);
        Page<MedicalRecord> records = medicalRecordRepository.findAllByPatientEmail(userEmail, pageable);

        log.debug("Found {} medical records", records.getTotalElements());
        return records.map(medicalRecordMapper::toMedicalRecordResponse);
    }
}

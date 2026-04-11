package com.darcode.curalink.service.impl;

import com.darcode.curalink.dto.medicalRecord.MedicalRecordResponse;
import com.darcode.curalink.enums.Role;
import com.darcode.curalink.exception.ResourceNotFoundException;
import com.darcode.curalink.mapper.MedicalRecordMapper;
import com.darcode.curalink.model.MedicalRecord;
import com.darcode.curalink.model.User;
import com.darcode.curalink.repository.MedicalRecordRepository;
import com.darcode.curalink.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    public Page<MedicalRecordResponse> findUserRecords(String userEmail, Pageable pageable) {
        log.debug("Searching medical records for user {}", userEmail);
        Page<MedicalRecord> records = medicalRecordRepository.findAllByPatientEmail(userEmail, pageable);

        log.debug("Found {} medical records", records.getTotalElements());
        return records.map(medicalRecordMapper::toMedicalRecordResponse);
    }

    @Override
    public Page<MedicalRecordResponse> findAllByPatient(Integer patientId, Pageable pageable) {
        User patient = userRepository.findByRoleAndId(Role.PATIENT, patientId).orElseThrow(
                () -> new ResourceNotFoundException("Patient", patientId)
        );

        log.debug("Searching medical records for patient {}", patient.getEmail());
        Page<MedicalRecord> records = medicalRecordRepository.findAllByPatientId(patientId, pageable);

        log.debug("Found {} medical records", records.getTotalElements());
        return records.map(medicalRecordMapper::toMedicalRecordResponse);
    }
}

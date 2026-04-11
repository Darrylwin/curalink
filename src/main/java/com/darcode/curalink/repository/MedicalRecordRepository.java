package com.darcode.curalink.repository;

import com.darcode.curalink.model.MedicalRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {

    @Query("SELECT mr FROM MedicalRecord mr WHERE mr.appointment.patient.email = :patientEmail")
    Page<MedicalRecord> findAllByPatientEmail(
            @Param("patientEmail") String patientEmail,
            Pageable pageable
    );

    @Query("SELECT mr FROM MedicalRecord mr WHERE mr.appointment.patient.id = :patientId")
    Page<MedicalRecord> findAllByPatientId(@Param("patientId") Integer patientId, Pageable pageable);
}

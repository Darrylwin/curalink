package com.darcode.curalink.repository;

import com.darcode.curalink.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    Page<Appointment> findAllByPatientEmailOrDoctorEmail(
            String patientEmail, String doctorEmail, Pageable pageable
    );
}

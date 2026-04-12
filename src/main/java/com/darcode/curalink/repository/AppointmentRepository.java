package com.darcode.curalink.repository;

import com.darcode.curalink.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    @Query("SELECT a FROM Appointment a WHERE a.doctor.email = :userEmail OR a.patient.email = :userEmail")
    Page<Appointment> findAllByUserAsDoctorOrPatient(
            @Param("userEmail") String userEmail,
            Pageable pageable
    );

    List<Appointment> findByTimeSlot_StartTimeBetween(LocalDateTime start, LocalDateTime end);
}

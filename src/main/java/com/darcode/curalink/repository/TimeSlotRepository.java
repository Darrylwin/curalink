package com.darcode.curalink.repository;

import com.darcode.curalink.model.TimeSlot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {

    Page<TimeSlot> findAllByIsAvailableAndDoctorId(Boolean isAvailable, Integer doctorId, Pageable pageable);

    Optional<TimeSlot> findByDoctorIdAndStartTimeAndEndTime(Integer doctorId, LocalDateTime startTime, LocalDateTime endTime);

    Optional<TimeSlot> findByIdAndDoctorId(Integer id, Integer doctorId);

    TimeSlot findByAppointmentId(Integer appointmentId);

    List<TimeSlot> findByIsAvailable(Boolean isAvailable);
}

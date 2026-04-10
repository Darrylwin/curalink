package com.darcode.curalink.repository;

import com.darcode.curalink.model.TimeSlot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {

    Page<TimeSlot> findAllByIsAvailableAndDoctorId(Boolean isAvailable, Integer doctorId, Pageable pageable);

    Optional<TimeSlot> findByDoctorIdAndStartTimeAndEndTime(Integer doctorId, LocalDateTime startTime, LocalDateTime endTime);

    Optional<TimeSlot> findByIdAndDoctorId(Integer id, Integer doctorId);
}

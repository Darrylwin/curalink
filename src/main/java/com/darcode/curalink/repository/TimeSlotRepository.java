package com.darcode.curalink.repository;

import com.darcode.curalink.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {

    List<TimeSlot> findAllByIsAvailableAndDoctorId(Boolean isAvailable, Integer doctorId);

    Optional<TimeSlot> findByDoctorIdAndStartTimeAndEndTime(Integer doctorId, LocalDateTime startTime, LocalDateTime endTime);
}

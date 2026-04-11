package com.darcode.curalink.service;

import com.darcode.curalink.dto.doctors.DefineTimeSlotRequest;
import com.darcode.curalink.dto.doctors.DefineTimeSlotResponse;
import com.darcode.curalink.dto.doctors.DoctorAvailableSlotResponse;
import com.darcode.curalink.dto.doctors.DoctorResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface DoctorService {

    DoctorResponse findById(Integer id);

    Page<DoctorResponse> findAllBySpecialityAndDisponibility(
            String speciality,
            Boolean disponibility,
            Pageable pageable
    );

    Page<DoctorAvailableSlotResponse> getAvailableSlots(Integer id, Pageable pageable);

    DefineTimeSlotResponse defineTimeSlot(Integer doctorId, DefineTimeSlotRequest defineTimeSlotRequest);
}
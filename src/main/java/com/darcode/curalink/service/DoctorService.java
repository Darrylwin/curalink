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
            Boolean disponiility,
            Pageable pageable
    );

    Page<DoctorAvailableSlotResponse> getAvailableSlots(Integer id, org.springframework.data.domain.Pageable pageable);

    DefineTimeSlotResponse defineTimeSlot(DefineTimeSlotRequest defineTimeSlotRequest);
}
package com.darcode.curalink.service;

import com.darcode.curalink.dto.doctors.DefineTimeSlotRequest;
import com.darcode.curalink.dto.doctors.DefineTimeSlotResponse;
import com.darcode.curalink.dto.doctors.DoctorAvailableSlotResponse;
import com.darcode.curalink.dto.doctors.DoctorResponse;

import java.util.List;

public interface DoctorService {
    List<DoctorResponse> findAll();

    DoctorResponse findById(Integer id);

    List<DoctorAvailableSlotResponse> getAvailableSlots(Integer id);

    DefineTimeSlotResponse defineTimeSlot(DefineTimeSlotRequest defineTimeSlotRequest);
}

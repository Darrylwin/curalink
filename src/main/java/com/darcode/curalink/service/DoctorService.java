package com.darcode.curalink.service;

import com.darcode.curalink.dto.doctors.DefineTimeSlotRequest;
import com.darcode.curalink.dto.doctors.DefineTimeSlotResponse;
import com.darcode.curalink.dto.doctors.DoctorAvailableSlotResponse;
import com.darcode.curalink.dto.doctors.DoctorResponse;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DoctorService {
    Page<DoctorResponse> findAllBySpecialityAndDisponibility(
            String speciality,
            Boolean disponiility,
            Pageable pageable
    );

    DoctorResponse findById(Integer id);

    List<DoctorAvailableSlotResponse> getAvailableSlots(Integer id);

    DefineTimeSlotResponse defineTimeSlot(DefineTimeSlotRequest defineTimeSlotRequest);
}

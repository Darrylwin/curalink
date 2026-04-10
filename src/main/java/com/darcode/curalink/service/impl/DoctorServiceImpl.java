package com.darcode.curalink.service.impl;

import com.darcode.curalink.dto.doctors.DefineTimeSlotRequest;
import com.darcode.curalink.dto.doctors.DefineTimeSlotResponse;
import com.darcode.curalink.dto.doctors.DoctorAvailableSlotResponse;
import com.darcode.curalink.dto.doctors.DoctorResponse;
import com.darcode.curalink.enums.Role;
import com.darcode.curalink.exception.ConflictException;
import com.darcode.curalink.exception.ResourceNotFoundException;
import com.darcode.curalink.mapper.DoctorMapper;
import com.darcode.curalink.model.TimeSlot;
import com.darcode.curalink.model.User;
import com.darcode.curalink.repository.TimeSlotRepository;
import com.darcode.curalink.repository.UserRepository;
import com.darcode.curalink.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final UserRepository userRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public Page<DoctorResponse> findAllBySpecialityAndDisponibility(
            String speciality,
            Boolean disponiility,
            Pageable pageable
    ) {
        Page<User> doctors = userRepository.findAllDoctorsBySpecialityAndDisponibility(
                speciality,
                disponiility,
                pageable
        );

        return doctors.map(doctorMapper::toDoctorResponse);
    }

    @Override
    public DoctorResponse findById(Integer id) {
        User doctor = userRepository.findByRoleAndId(Role.DOCTOR, id).orElseThrow(() -> new ResourceNotFoundException("Doctor", id));

        return doctorMapper.toDoctorResponse(doctor);
    }

    @Override
    public Page<DoctorAvailableSlotResponse> getAvailableSlots(Integer id, Pageable pageable) {
        userRepository.findByRoleAndId(Role.DOCTOR, id).orElseThrow(() -> new ResourceNotFoundException("Doctor", id));

        Page<TimeSlot> timeSlots = timeSlotRepository.findAllByIsAvailableAndDoctorId(true, id, pageable);

        return timeSlots
                .map(doctorMapper::toAvailableSlotResponse);
    }

    @Override
    public DefineTimeSlotResponse defineTimeSlot(DefineTimeSlotRequest request) {
        User doctor = userRepository.findByRoleAndId(Role.DOCTOR, request.doctorId()).orElseThrow(
                () -> new ResourceNotFoundException("Doctor", request.doctorId())
        );

        TimeSlot slotReserved = timeSlotRepository.findByDoctorIdAndStartTimeAndEndTime(
                request.doctorId(),
                request.startTime(),
                request.endTime()
        ).orElse(null);

        if (slotReserved == null) {
            TimeSlot definedTimeSlot = new TimeSlot();
            definedTimeSlot.setStartTime(request.startTime());
            definedTimeSlot.setEndTime(request.endTime());
            definedTimeSlot.setIsAvailable(true);
            definedTimeSlot.setDoctor(doctor);

            TimeSlot savedTimeSlot = timeSlotRepository.save(definedTimeSlot);
            return doctorMapper.toDefinedTimeDlot(savedTimeSlot);
        } else {
            throw new ConflictException("This time slot is already registered for this doctor");
        }
    }
}

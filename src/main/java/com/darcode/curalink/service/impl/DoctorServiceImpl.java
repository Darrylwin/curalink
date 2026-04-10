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
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final UserRepository userRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public List<DoctorResponse> findAll() {
        List<User> doctors = userRepository.findAllByRole(Role.DOCTOR);

        return doctors.stream()
                .map(doctorMapper::toDoctorResponse)
                .toList();
    }

    @Override
    public DoctorResponse findById(Integer id) {
        User doctor = userRepository.findByRoleAndId(Role.DOCTOR, id).orElseThrow(() -> new ResourceNotFoundException("Doctor", id));

        return doctorMapper.toDoctorResponse(doctor);
    }

    @Override
    public List<DoctorAvailableSlotResponse> getAvailableSlots(Integer id) {
        User doctor = userRepository.findByRoleAndId(Role.DOCTOR, id).orElseThrow(() -> new ResourceNotFoundException("Doctor", id));

        List<TimeSlot> timeSlots = timeSlotRepository.findAllByIsAvailableAndDoctorId(true, id);

        return timeSlots.stream()
                .map(doctorMapper::toAvailableSlotResponse)
                .toList();
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
            return new DefineTimeSlotResponse(
                    savedTimeSlot.getDoctor().getId(),
                    savedTimeSlot.getStartTime(),
                    savedTimeSlot.getEndTime(),
                    Duration.between(savedTimeSlot.getStartTime(), savedTimeSlot.getEndTime()),
                    savedTimeSlot.getIsAvailable()
            );
        } else {
            throw new ConflictException("This time slot is already registered for this doctor");
        }
    }
}

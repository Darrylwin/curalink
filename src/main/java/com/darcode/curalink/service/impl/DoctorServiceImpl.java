package com.darcode.curalink.service.impl;

import com.darcode.curalink.dto.doctors.DoctorResponse;
import com.darcode.curalink.enums.Role;
import com.darcode.curalink.exception.ResourceNotFoundException;
import com.darcode.curalink.mapper.DoctorMapper;
import com.darcode.curalink.model.User;
import com.darcode.curalink.repository.UserRepository;
import com.darcode.curalink.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final UserRepository userRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public List<DoctorResponse> findAll() {
        List<User> doctors = userRepository.findAllByRole(Role.DOCTOR);

        return doctors.stream()
                .map(doctorMapper::toResponse)
                .toList();
    }

    @Override
    public DoctorResponse findById(Integer id) {
        User doctor = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor", id));

        return doctorMapper.toResponse(doctor);
    }
}

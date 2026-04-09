package com.darcode.curalink.service;

import com.darcode.curalink.dto.doctors.DoctorResponse;

import java.util.List;

public interface DoctorService {
    public List<DoctorResponse> findAll();
}

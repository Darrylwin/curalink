package com.darcode.curalink.repository;

import com.darcode.curalink.model.DoctorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, Integer> {
}

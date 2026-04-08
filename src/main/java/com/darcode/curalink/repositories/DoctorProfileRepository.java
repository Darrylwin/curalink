package com.darcode.curalink.repositories;

import com.darcode.curalink.entities.DoctorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, Integer> {
}

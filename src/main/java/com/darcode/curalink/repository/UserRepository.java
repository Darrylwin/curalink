package com.darcode.curalink.repository;

import com.darcode.curalink.enums.Role;
import com.darcode.curalink.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("""
        SELECT DISTINCT u FROM User u
        LEFT JOIN u.timeSlots ts
        LEFT JOIN u.doctorProfile dp
        WHERE u.role = com.darcode.curalink.enums.Role.DOCTOR
        AND (:speciality IS NULL OR dp.speciality = :speciality)
        AND (:disponibility IS NULL\s
        OR (:disponibility = true AND ts.isAvailable = true AND ts.startTime >= CURRENT_TIMESTAMP)
        OR (:disponibility = false AND (ts.isAvailable = false OR ts.startTime < CURRENT_TIMESTAMP)))
           \s""")
    Page<User> findAllDoctorsBySpecialityAndDisponibility(
            @Param("speciality") String speciality,
            @Param("disponibility") Boolean disponibility,
            Pageable pageable
    );

    Optional<User> findByEmail(String email);

    Optional<User> findByRoleAndId(Role role, Integer id);

    List<User> findByRole(Role role);
}

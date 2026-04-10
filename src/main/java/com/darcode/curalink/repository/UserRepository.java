package com.darcode.curalink.repository;

import com.darcode.curalink.enums.Role;
import com.darcode.curalink.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    
    List<User> findAllByRole(Role role);

    Optional<User> findByRoleAndId(Role role, Integer id);
}

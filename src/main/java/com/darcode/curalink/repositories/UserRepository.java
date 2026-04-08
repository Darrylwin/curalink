package com.darcode.curalink.repositories;

import com.darcode.curalink.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}

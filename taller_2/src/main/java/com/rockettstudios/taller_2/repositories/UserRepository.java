package com.rockettstudios.taller_2.repositories;

import com.rockettstudios.taller_2.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsernameOrEmail(String username, String email);
}

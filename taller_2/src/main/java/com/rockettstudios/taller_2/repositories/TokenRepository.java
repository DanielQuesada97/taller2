package com.rockettstudios.taller_2.repositories;


import com.rockettstudios.taller_2.domains.entities.Token;
import com.rockettstudios.taller_2.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {
    List<Token> findByUserAndActive(User user, Boolean active);
}

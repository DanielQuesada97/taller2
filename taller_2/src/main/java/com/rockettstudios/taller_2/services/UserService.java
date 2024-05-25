package com.rockettstudios.taller_2.services;

import com.rockettstudios.taller_2.domains.dtos.UserRegisterDTO;

import com.rockettstudios.taller_2.domains.entities.Token;
import com.rockettstudios.taller_2.domains.entities.User;
import jakarta.transaction.Transactional;

public interface UserService {
    User findByIdentifier(String identifier);
    User findByUsernameOrEmail(String username, String email);
    void register(UserRegisterDTO userRegisterDTO);
    boolean checkPassword(User user, String password);

    //Token management
    Token registerToken(User user) throws Exception;
    Boolean isTokenValid(User user, String token);
    void cleanTokens(User user) throws Exception;

    @Transactional(rollbackOn = Exception.class)
    //void register(UserRegisterDTO UserInfo);

    //Find User authenticated
    User findUserAuthenticated();
}

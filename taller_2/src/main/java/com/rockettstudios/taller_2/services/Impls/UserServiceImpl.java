package com.rockettstudios.taller_2.services.Impls;


import com.rockettstudios.taller_2.domains.dtos.UserRegisterDTO;
import com.rockettstudios.taller_2.domains.entities.Token;
import com.rockettstudios.taller_2.domains.entities.User;
import com.rockettstudios.taller_2.repositories.TokenRepository;
import com.rockettstudios.taller_2.repositories.UserRepository;
import com.rockettstudios.taller_2.services.UserService;
import com.rockettstudios.taller_2.utils.JWTTools;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private TokenRepository tokenRepository;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByIdentifier(String identifier) {
        return userRepository.findByUsernameOrEmail(identifier, identifier).orElse(null);
    }

    @Override
    public User findByUsernameOrEmail(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, email).orElse(null);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void register(UserRegisterDTO UserInfo) {
        User user = new User();

        user.setUsername(UserInfo.getUsername());
        user.setEmail(UserInfo.getEmail());
        user.setPassword(UserInfo.getPassword());

        userRepository.save(user);
    }

    @Override
    public boolean checkPassword(User user, String password) {
        return user.getPassword().equals(password);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Token registerToken(User user) throws Exception {
        cleanTokens(user);

        String tokenString = jwtTools.generateToken(user);
        Token token = new Token(tokenString, user);

        tokenRepository.save(token);

        return token;
    }

    @Override
    public Boolean isTokenValid(User user, String token) {
        try {
            cleanTokens(user);
            List<Token> tokens = tokenRepository.findByUserAndActive(user, true);

            tokens.stream()
                    .filter(tk -> tk.getContent().equals(token))
                    .findAny()
                    .orElseThrow(() -> new Exception());

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void cleanTokens(User user) throws Exception {
        List<Token> tokens = tokenRepository.findByUserAndActive(user, true);

        tokens.forEach(token -> {
            if(!jwtTools.verifyToken(token.getContent())) {
                token.setActive(false);
                tokenRepository.save(token);
            }
        });

    }
    @Override
    public User findUserAuthenticated() {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByUsernameOrEmail(username, username).orElse(null);
    }
}


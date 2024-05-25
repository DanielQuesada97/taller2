package com.rockettstudios.taller_2.controllers;


import com.rockettstudios.taller_2.domains.dtos.GeneralResponse;
import com.rockettstudios.taller_2.domains.dtos.LoginDTO;
import com.rockettstudios.taller_2.domains.dtos.TokenDTO;
import com.rockettstudios.taller_2.domains.dtos.UserRegisterDTO;
import com.rockettstudios.taller_2.domains.entities.Token;
import com.rockettstudios.taller_2.domains.entities.User;
import com.rockettstudios.taller_2.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<GeneralResponse> login(@RequestBody @Valid LoginDTO info) {
        User user = userService.findByUsernameOrEmail(info.getIdentifier(), info.getIdentifier());

        if(user == null) {
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "User not found");
        }

        if(!userService.checkPassword(user, info.getPassword())) {
            return GeneralResponse.getResponse(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
//Token
        try {
            Token token = userService.registerToken(user);
            return GeneralResponse.getResponse(new TokenDTO(token));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<GeneralResponse> register(@RequestBody @Valid UserRegisterDTO info) {
        User user = userService.findByUsernameOrEmail(info.getUsername(), info.getEmail());

        if(user != null) {
            return GeneralResponse.getResponse(HttpStatus.CONFLICT, "User already exists");
        }

        userService.register(info);

        return GeneralResponse.getResponse(HttpStatus.CREATED, "User registered successfully");
    }
}

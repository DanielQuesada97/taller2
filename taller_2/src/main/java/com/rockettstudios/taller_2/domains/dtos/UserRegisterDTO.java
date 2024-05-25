package com.rockettstudios.taller_2.domains.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterDTO {
    @NotBlank private String username;
    @NotBlank private String password;  // TODO: Generar un patron seguro
    @NotBlank
    private String email;
}

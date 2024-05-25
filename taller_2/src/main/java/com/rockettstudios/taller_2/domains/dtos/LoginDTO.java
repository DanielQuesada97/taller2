package com.rockettstudios.taller_2.domains.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
    @NotBlank
    private String identifier;

    @NotBlank
    private String password;
}

package com.rockettstudios.taller_2.domains.dtos;

import com.rockettstudios.taller_2.domains.entities.Token;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenDTO {

    private String token;

    public TokenDTO(Token token) {
        this.token = token.getContent();
    }

}

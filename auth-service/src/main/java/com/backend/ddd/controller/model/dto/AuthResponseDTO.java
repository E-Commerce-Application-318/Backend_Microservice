package com.backend.ddd.controller.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class AuthResponseDTO {
    private String username;
    private String role;
//    private String token;
//    private String refreshToken;
}

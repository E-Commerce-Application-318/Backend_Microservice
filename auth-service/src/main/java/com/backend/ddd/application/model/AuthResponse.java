package com.backend.ddd.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private boolean success;
    private String message;
    private String username;
    private String role;
}

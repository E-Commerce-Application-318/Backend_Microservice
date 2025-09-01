package com.backend.ddd.controller.model.dto;
import lombok.Data;

@Data
public class LoginRequestDTO {
    private String username;
    private String password;
}

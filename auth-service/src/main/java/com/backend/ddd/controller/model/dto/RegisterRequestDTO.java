package com.backend.ddd.controller.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain=true)
public class RegisterRequestDTO {
    private String username;
    private String password;
    private String confirmedPassword;
    private String name;
    private String email;
    private String phoneNumber;
    private String role;
    private Date birthDate;
}

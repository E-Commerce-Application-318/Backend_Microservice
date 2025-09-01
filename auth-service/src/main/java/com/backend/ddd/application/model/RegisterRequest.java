package com.backend.ddd.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain=true)
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String confirmedPassword;
    private String name;
    private String email;
    private String phoneNumber;
    private String role;
    private Date birthDate;
}

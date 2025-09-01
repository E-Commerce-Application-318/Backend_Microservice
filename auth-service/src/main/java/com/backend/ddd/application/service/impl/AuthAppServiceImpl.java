package com.backend.ddd.application.service.impl;

import com.backend.ddd.application.model.AuthResponse;
import com.backend.ddd.application.model.LoginRequest;
import com.backend.ddd.application.model.RegisterRequest;
import com.backend.ddd.application.service.AuthAppService;
import com.backend.ddd.domain.model.entity.User;
import com.backend.ddd.domain.service.AuthDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthAppServiceImpl implements AuthAppService {

    @Autowired
    AuthDomainService authDomainService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Optional<User> user = authDomainService.getUserByUsername(loginRequest.getUsername());
        if (user.isEmpty()) {
            return new AuthResponse()
                    .setSuccess(false)
                    .setMessage("Username not found");
        }
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            return new AuthResponse()
                    .setSuccess(false)
                    .setMessage("Wrong password");
        }

        return new AuthResponse()
                .setUsername(user.get().getUsername())
                .setRole(user.get().getRole())
                .setMessage("Login successful")
                .setSuccess(true);
    }

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        if (authDomainService.existsByUsername(registerRequest.getUsername())) {
             return new AuthResponse()
                    .setSuccess(false)
                    .setMessage("Username already exists");
        }
        if (authDomainService.existsByEmail(registerRequest.getEmail())) {
            return new AuthResponse()
                    .setSuccess(false)
                    .setMessage("Email already exists");
        }
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmedPassword())) {
            return new AuthResponse()
                    .setSuccess(false)
                    .setMessage("Password and confirmed password don't match");
        }
        User newUser = new User()
                .setUsername(registerRequest.getUsername())
                .setPassword(registerRequest.getPassword())
                .setName(registerRequest.getName())
                .setEmail(registerRequest.getEmail())
                .setPhoneNumber(registerRequest.getPhoneNumber())
                .setRole(registerRequest.getRole())
                .setBirthDate(registerRequest.getBirthDate());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        User user = authDomainService.registerUser(newUser);

        if (user == null) {
            return new AuthResponse()
                    .setSuccess(false)
                    .setMessage("Failed to register user");
        }
        return new AuthResponse()
                .setSuccess(true)
                .setMessage("Register successful")
                .setUsername(user.getUsername())
                .setRole(user.getRole());
    }
}

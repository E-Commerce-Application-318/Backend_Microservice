package com.backend.ddd.application.service;

import com.backend.ddd.application.model.AuthResponse;
import com.backend.ddd.application.model.LoginRequest;
import com.backend.ddd.application.model.RegisterRequest;

public interface AuthAppService {
    AuthResponse login(LoginRequest loginRequest);
    AuthResponse register(RegisterRequest registerRequest);
}

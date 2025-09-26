package com.backend.ddd.application.service;

import com.backend.ddd.application.model.AuthResponse;
import com.backend.ddd.application.model.LoginRequest;
import com.backend.ddd.application.model.RegisterRequest;
import com.backend.ddd.controller.model.dto.PaymentRequestDTO;
import com.backend.ddd.controller.model.dto.UserDetailResponseDTO;

import java.util.UUID;

public interface AuthAppService {
    AuthResponse login(LoginRequest loginRequest);
    AuthResponse register(RegisterRequest registerRequest);
    UserDetailResponseDTO getUserDetail(UUID userId);
    Boolean processPayment(UUID userId, PaymentRequestDTO paymentRequestDTO);
}

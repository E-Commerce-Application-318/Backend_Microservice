package com.backend.ddd.application.service;

import com.backend.ddd.application.model.AuthResponse;
import com.backend.ddd.application.model.LoginRequest;
import com.backend.ddd.application.model.RegisterRequest;
import com.backend.ddd.controller.model.dto.UserDetailResponseDTO;
import com.backend.shared.domain.order_product.PaymentDetail;

import java.util.UUID;

public interface AuthAppService {
    AuthResponse login(LoginRequest loginRequest);
    AuthResponse register(RegisterRequest registerRequest);
    UserDetailResponseDTO getUserDetail(UUID userId);
    String processPayment(PaymentDetail paymentDetail);
    String refundPayment(UUID userId, Double totalAmount);
}

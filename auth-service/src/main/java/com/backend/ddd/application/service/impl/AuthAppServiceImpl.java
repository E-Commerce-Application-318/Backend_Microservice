package com.backend.ddd.application.service.impl;

import com.backend.ddd.application.model.AuthResponse;
import com.backend.ddd.application.model.LoginRequest;
import com.backend.ddd.application.model.RegisterRequest;
import com.backend.ddd.application.service.AuthAppService;
import com.backend.ddd.controller.model.dto.UserDetailResponseDTO;
import com.backend.ddd.domain.model.entity.Payment;
import com.backend.ddd.domain.model.entity.User;
import com.backend.ddd.domain.service.AuthDomainService;
import com.backend.shared.domain.order_product.PaymentDetail;
import com.backend.shared.domain.order_product.PaymentStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class AuthAppServiceImpl implements AuthAppService {

    @Autowired
    private AuthDomainService authDomainService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthEventPublisher authEventPublisher;

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

    @Override
    public UserDetailResponseDTO getUserDetail(UUID userId) {
        Optional<User> user = authDomainService.getUserDetail(userId);
        if (user.isEmpty()) {
            return null;
        }
        return new UserDetailResponseDTO(
                user.get().getName(),
                user.get().getAddress(),
                user.get().getPhoneNumber()
        );
    }

    @Override
    public String processPayment(PaymentDetail paymentDetail) {
        Payment payment = authDomainService.getPaymentByUserId(paymentDetail.getUserId());

        if (payment.getCardNumber().equals(paymentDetail.getCardNumber())
                & payment.getCardHolderName().equals(paymentDetail.getCardHolderName())
                & payment.getExpiryDate().equals(paymentDetail.getExpiryDate())
                & payment.getCvv().equals(paymentDetail.getCvv())) {
            if (payment.getBalance() >= paymentDetail.getTotalAmount()) {
                payment.setBalance(payment.getBalance() - paymentDetail.getTotalAmount());
                if (authDomainService.savePayment(payment) != null) {
                    // publish event to update the status of order
                    authEventPublisher.handleStatusUpdatedEvent(new PaymentStatus(paymentDetail.getOrderId(), "SUCCESS"));
                    return "Payment successful";
                } else {
                    authEventPublisher.handleStatusUpdatedEvent(new PaymentStatus(paymentDetail.getOrderId(), "Failed. Error when update the balance"));
                    return "Payment failed because cannot update balance";
                }
            } else {
                authEventPublisher.handleStatusUpdatedEvent(new PaymentStatus(paymentDetail.getOrderId(), "Failed. Not enough balance"));
                return "Not enough balance in this card !!!";
            }
        } else {
            authEventPublisher.handleStatusUpdatedEvent(new PaymentStatus(paymentDetail.getOrderId(), "Failed. Payment detail is incorrect"));
            return "Payment detail is incorrect";
        }
    }

    public String refundPayment(UUID userId, Double totalAmount) {
        Payment payment = authDomainService.getPaymentByUserId(userId);
        payment.setBalance(payment.getBalance() + totalAmount);
        if (authDomainService.savePayment(payment) != null) {
            return "Refund successful";
        } else {
            return "Refund failed because cannot update balance";
        }
    }
}

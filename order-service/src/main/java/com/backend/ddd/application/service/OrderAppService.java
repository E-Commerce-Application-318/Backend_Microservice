package com.backend.ddd.application.service;

import com.backend.ddd.controller.model.dto.OrderResponseDTO;
import com.backend.ddd.controller.model.dto.PaymentRequestDTO;

import java.util.List;
import java.util.UUID;

public interface OrderAppService {
    List<OrderResponseDTO> getAllOrdersByUserId(UUID userId);
    OrderResponseDTO createOrder(UUID userId, List<UUID> cartIds);
    String updateOrderAddressPhoneNumber(UUID userId, String address, String phoneNumber);
    String processPayment(UUID orderId, UUID userId, PaymentRequestDTO paymentRequestDTO);
    Boolean cancelOrder(UUID orderId);
    void updateOrderStatus(UUID orderId, String status);
}

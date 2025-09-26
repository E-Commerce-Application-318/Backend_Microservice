package com.backend.ddd.application.service;

import com.backend.ddd.controller.model.dto.OrderResponseDTO;

import java.util.List;
import java.util.UUID;

public interface OrderAppService {
    OrderResponseDTO createOrder(UUID userId, List<UUID> cartIds);
    String updateOrderAddressPhoneNumber(UUID userId, String address, String phoneNumber);
}

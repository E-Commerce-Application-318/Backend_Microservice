package com.backend.ddd.application.service;

import com.backend.ddd.infrastructure.persistence.client.model.ExternalOrderResponse;

import java.util.List;
import java.util.UUID;

public interface AgentAppService {
    List<ExternalOrderResponse> getAllOrdersByUserId(UUID userId);
    ExternalOrderResponse createOrder(UUID userId, List<UUID> cartIds);
    String updateOrder(UUID orderId, String address, String phoneNumber);
    String cancelOrder(UUID orderId);
}

package com.backend.ddd.application.service;

import com.backend.ddd.infrastructure.persistence.client.model.ExternalOrderResponse;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalProductResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AgentAppService {
    List<ExternalOrderResponse> getAllOrdersByUserId(UUID userId);
    List<ExternalProductResponse> getAllProducts();
    ExternalOrderResponse createOrder(UUID userId, Map<UUID, Integer> productIdAndQuantity);
    String updateOrder(UUID orderId, String address, String phoneNumber);
    String cancelOrder(UUID orderId);
}

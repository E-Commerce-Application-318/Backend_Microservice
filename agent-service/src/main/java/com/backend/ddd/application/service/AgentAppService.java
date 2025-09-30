package com.backend.ddd.application.service;

import com.backend.ddd.infrastructure.persistence.client.model.ExternalOrderResponse;

import java.util.List;
import java.util.UUID;

public interface AgentAppService {
    List<ExternalOrderResponse> getAllOrdersByUserId(UUID userId);
}

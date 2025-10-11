package com.backend.ddd.application.service.impl;

import com.backend.ddd.application.service.AgentAppService;
import com.backend.ddd.infrastructure.persistence.client.OrderClient;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalOrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AgentAppServiceImpl implements AgentAppService {

    @Autowired
    private OrderClient orderClient;

    @Override
    public List<ExternalOrderResponse> getAllOrdersByUserId(UUID userId) {
        List<ExternalOrderResponse> externalOrderResponseList = orderClient.getAllOrders(userId);
        return externalOrderResponseList;
    }

    @Override
    public ExternalOrderResponse createOrder(UUID userId, List<UUID> cartIds) {
        ExternalOrderResponse externalOrderResponse = orderClient.createOrder(userId, cartIds);
        return externalOrderResponse;
    }
    @Override
    public String updateOrder(UUID orderId, String address, String phoneNumber) {
        String result = orderClient.updateOrder(orderId, address, phoneNumber);
        return result;
    }

    @Override
    public String cancelOrder(UUID orderId) {
        String result = orderClient.cancelOrder(orderId);
        return result;
    }
}

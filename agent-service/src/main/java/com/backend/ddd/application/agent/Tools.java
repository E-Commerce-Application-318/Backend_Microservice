package com.backend.ddd.application.agent;

import com.backend.ddd.application.service.AgentAppService;
import com.backend.ddd.infrastructure.persistence.client.CartClient;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalCartResponse;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalOrderResponse;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalProductResponse;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class Tools {

    @Autowired
    private AgentAppService agentAppService;

//    @Tool
//    public String getAllCarts(UUID userId) {
//        return "List of cart";
//    }

    @Tool
    public List<ExternalOrderResponse> getAllOrdersByUserId(UUID userId) {
        return agentAppService.getAllOrdersByUserId(userId);
    }

    @Tool
    public List<ExternalProductResponse> getAllProducts(UUID userId) {
        return agentAppService.getAllProducts();
    }
    @Tool
    public String createOrderWithUserIdAndProductIds(
            UUID userId, Map<UUID, Integer> productIdAndQuantity
    ) {
        ExternalOrderResponse externalOrderResponse = agentAppService.createOrder(userId, productIdAndQuantity);
        return "Create Order By userId" + externalOrderResponse;
    }

    @Tool
    public String updateOrder(UUID orderId, String address, String phoneNumber) {
        String result = agentAppService.updateOrder(orderId, address, phoneNumber);
        return "Update order by address: " + address + ", phoneNumber: " + phoneNumber + ". " + result;
    }

    @Tool
    public String cancelOrder(UUID orderId) {
        String result = agentAppService.cancelOrder(orderId);
        return "Cancel order: " + orderId + " " + result;
    }
}

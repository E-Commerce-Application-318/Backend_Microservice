package com.backend.ddd.application.agent;

import com.backend.ddd.application.service.AgentAppService;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalOrderResponse;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class Tools {

    @Autowired
    private AgentAppService agentAppService;

    @Tool
    public String getAllCarts(UUID userId) {
        return "List of cart";
    }

    @Tool
    public String getAllOrdersByUserId(UUID userId) {
        return "Get all orders Check user ID: 123";
//        return agentAppService.getAllOrdersByUserId(userId);
    }
    @Tool
    public String createOrderByUserId(UUID userId, List<UUID> cartIds) {
        return "Create Order By userId";
    }

    @Tool
    public String createOrderByUsername(String username, List<UUID> cardIds) {
        return "Create Order By username";
    }

    @Tool
    public String updateOrder(String address, String phoneNumber) {
        return "Update order by address: " + address + ", phoneNumber: " + phoneNumber;
    }

    @Tool
    public String cancelOrder(UUID orderId) {
        return  "Cancel order: " + orderId;
    }
}

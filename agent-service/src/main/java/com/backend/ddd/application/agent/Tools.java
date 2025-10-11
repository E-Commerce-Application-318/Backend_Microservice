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

//    @Tool
//    public String getAllCarts(UUID userId) {
//        return "List of cart";
//    }

    @Tool
    public List<ExternalOrderResponse> getAllOrdersByUserId(UUID userId) {
        return agentAppService.getAllOrdersByUserId(userId);
    }
    @Tool
    public String createOrderWithUserIdAndCartIds(
            UUID userId, List<UUID> cartIds
    ) {
        agentAppService.createOrder(userId, cartIds);
        return "Create Order By userId";
    }

    @Tool
    public String updateOrder(UUID orderId, String address, String phoneNumber) {
        return "Update order by address: " + address + ", phoneNumber: " + phoneNumber;
    }

    @Tool
    public String cancelOrder(UUID orderId) {
        return  "Cancel order: " + orderId;
    }
}

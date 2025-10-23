package com.backend.ddd.application.service.impl;

import com.backend.ddd.application.service.AgentAppService;
import com.backend.ddd.infrastructure.persistence.client.CartClient;
import com.backend.ddd.infrastructure.persistence.client.OrderClient;
import com.backend.ddd.infrastructure.persistence.client.ProductClient;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalCartResponse;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalOrderResponse;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class AgentAppServiceImpl implements AgentAppService {

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private CartClient cartClient;

    @Override
    public List<ExternalOrderResponse> getAllOrdersByUserId(UUID userId) {
        List<ExternalOrderResponse> externalOrderResponseList = orderClient.getAllOrders(userId);
        return externalOrderResponseList;
    }

    @Override
    public List<ExternalProductResponse> getAllProducts() {
        return productClient.getAllProducts();
    }
    @Override
    public ExternalOrderResponse createOrder(UUID userId, Map<UUID, Integer> productIdAndQuantity) {
        System.out.println("Create Order By userId " + userId + " + " + productIdAndQuantity);
        List<UUID> cartIds = new ArrayList<>();
        for (Map.Entry<UUID, Integer> entry : productIdAndQuantity.entrySet()) {
            ExternalCartResponse externalCartResponse = cartClient.addProductToCart(userId, entry.getKey(), entry.getValue());
            System.out.println("Added product to cart " + externalCartResponse);
            cartIds.add(externalCartResponse.getId());
        }
        ExternalOrderResponse externalOrderResponse = orderClient.createOrder(userId, cartIds);
        System.out.println("Order created + " + externalOrderResponse);
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

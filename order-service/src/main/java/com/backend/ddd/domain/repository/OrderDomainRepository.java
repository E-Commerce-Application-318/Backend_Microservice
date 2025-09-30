package com.backend.ddd.domain.repository;

import com.backend.ddd.domain.model.entity.Order;
import com.backend.ddd.domain.model.entity.OrderItem;

import java.util.List;
import java.util.UUID;

public interface OrderDomainRepository {
    Order getOrderById(UUID orderId);
    Order saveOrder(Order order);
    List<Order> findOrdersByUserId(UUID userId);
    List<OrderItem> saveOrderItems(List<OrderItem> orderItems);
    List<OrderItem> getOrderItemsByOrderId(UUID orderId);
    void processPayment(UUID orderId);
}


package com.backend.ddd.domain.service;

import com.backend.ddd.domain.model.entity.Order;
import com.backend.ddd.domain.model.entity.OrderItem;

import java.util.List;
import java.util.UUID;

public interface OrderDomainService {
    Order getOrderById(UUID orderId);
//    List<Order> getOrdersByUserId(UUID userId);
    Order saveOrder(Order order);
    List<OrderItem> saveOrderItems(List<OrderItem> orderItems);
}

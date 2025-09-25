package com.backend.ddd.domain.service;

import com.backend.ddd.domain.model.entity.Order;
import com.backend.ddd.domain.model.entity.OrderItem;

import java.util.List;
import java.util.UUID;


public interface OrderDomainService {
    Order saveOrder(Order order);
    List<OrderItem> saveOrderItems(List<OrderItem> orderItems);

    Order findOrderById(UUID orderId);
    List<OrderItem> findOrderItemsByOrderId(UUID orderId);
    void deleteOrderItemsByOrderId(UUID orderId);
    void deleteOrderById(UUID orderId);

}

package com.backend.ddd.domain.repository;

import com.backend.ddd.domain.model.entity.Order;
import com.backend.ddd.domain.model.entity.OrderItem;

import java.util.List;
import java.util.UUID;


public interface OrderDomainRepository {
    Order saveOrder(Order order);
    List<OrderItem> saveOrderItems(List<OrderItem> orderItems);

    /** Find an order by its id */
    Order findOrderById(UUID orderId);

    /** Find all items belonging to a specific order */
    List<OrderItem> findOrderItemsByOrderId(UUID orderId);

    /** Delete all order items of a specific order */
    void deleteOrderItemsByOrderId(UUID orderId);

    /** Delete an order by its id */
    void deleteOrderById(UUID orderId);




}


package com.backend.ddd.domain.repository;

import com.backend.ddd.domain.model.entity.Order;
import com.backend.ddd.domain.model.entity.OrderItem;

import java.util.List;

public interface OrderDomainRepository {
    Order saveOrder(Order order);
    List<OrderItem> saveOrderItems(List<OrderItem> orderItems);
}


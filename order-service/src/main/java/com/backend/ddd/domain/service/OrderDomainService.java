package com.backend.ddd.domain.service;

import com.backend.ddd.domain.model.entity.Order;
import com.backend.ddd.domain.model.entity.OrderItem;

import java.util.List;

public interface OrderDomainService {
    Order saveOrder(Order order);
    List<OrderItem> saveOrderItems(List<OrderItem> orderItems);
}

package com.backend.ddd.domain.service.impl;

import com.backend.ddd.domain.model.entity.Order;
import com.backend.ddd.domain.model.entity.OrderItem;
import com.backend.ddd.domain.repository.OrderDomainRepository;
import com.backend.ddd.domain.service.OrderDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderDomainServiceImpl implements OrderDomainService {

    @Autowired
    private OrderDomainRepository orderDomainRepository;

    @Override
    public Order saveOrder(Order order) {
        return orderDomainRepository.saveOrder(order);
    }

    @Override
    public List<OrderItem> saveOrderItems(List<OrderItem> orderItems) {
        return orderDomainRepository.saveOrderItems(orderItems);
    }

    @Override
    public Order findOrderById(UUID orderId) {
        return orderDomainRepository.findOrderById(orderId);
    }

    @Override
    public List<OrderItem> findOrderItemsByOrderId(UUID orderId) {
        return orderDomainRepository.findOrderItemsByOrderId(orderId);
    }

    @Override
    public void deleteOrderItemsByOrderId(UUID orderId) {
        orderDomainRepository.deleteOrderItemsByOrderId(orderId);
    }

    @Override
    public void deleteOrderById(UUID orderId) {
        orderDomainRepository.deleteOrderById(orderId);
    }

}

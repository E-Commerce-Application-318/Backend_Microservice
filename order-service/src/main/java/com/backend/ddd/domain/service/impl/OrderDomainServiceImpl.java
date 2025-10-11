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
    public Order getOrderById(UUID orderId) {
        return orderDomainRepository.getOrderById(orderId);
    }
    @Override
    public List<Order> getOrdersByUserId(UUID userId) {
        return orderDomainRepository.findOrdersByUserId(userId);
    }

    @Override
    public Order saveOrder(Order order) {
        return orderDomainRepository.saveOrder(order);
    }

    @Override
    public List<OrderItem> saveOrderItems(List<OrderItem> orderItems) {
        return orderDomainRepository.saveOrderItems(orderItems);
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(UUID orderId) {
        return orderDomainRepository.getOrderItemsByOrderId(orderId);
    }

    @Override
    public void processPayment(UUID orderId) {
        orderDomainRepository.processPayment(orderId);
    }

    @Override
    public void cancelOrder(UUID orderId) {
        orderDomainRepository.cancelOrder(orderId);
    }

    @Override
    public void updateOrderStatus(UUID orderId, String status) {
        orderDomainRepository.updateOrderStatus(orderId, status);
    }

}

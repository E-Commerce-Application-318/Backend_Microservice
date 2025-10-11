package com.backend.ddd.infrastructure.persistence.repository;

import com.backend.ddd.domain.model.entity.Order;
import com.backend.ddd.domain.model.entity.OrderItem;
import com.backend.ddd.domain.repository.OrderDomainRepository;
import com.backend.ddd.infrastructure.persistence.mapper.OrderItemJPAMapper;
import com.backend.ddd.infrastructure.persistence.mapper.OrderJPAMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Repository
public class OrderInfrasRepositoryImpl implements OrderDomainRepository {

    @Autowired
    private OrderJPAMapper orderJPAMapper;

    @Autowired
    private OrderItemJPAMapper orderItemJPAMapper;

    @Override
    public Order getOrderById(UUID orderId) {
        return orderJPAMapper.findById(orderId).orElse(null);
    }

    @Override
    public List<Order> findOrdersByUserId(UUID userId) {
        return orderJPAMapper.findOrdersByUserId(userId);
    }

    @Override
    public Order saveOrder(Order order) {
        return orderJPAMapper.save(order);
    }

    @Override
    public List<OrderItem> saveOrderItems(List<OrderItem> orderItems) {
        return orderItemJPAMapper.saveAll(orderItems);
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(UUID orderId) {
        return orderItemJPAMapper.findAllByOrderId(orderId);
    }

    @Override
    public void processPayment(UUID orderId) {
        orderJPAMapper.processPayment(orderId);
    }

    @Override
    public void cancelOrder(UUID orderId) {
        orderJPAMapper.cancelOrder(orderId);
    }

    @Override
    public void updateOrderStatus(UUID orderId, String status) {
        orderJPAMapper.updateOrderStatus(orderId, status);
    }
}

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
    public Order saveOrder(Order order) {
        return orderJPAMapper.save(order);
    }

    @Override
    public List<OrderItem> saveOrderItems(List<OrderItem> orderItems) {
        return orderItemJPAMapper.saveAll(orderItems);
    }


    // add new code

    @Override
    public Order findOrderById(UUID orderId) {
        return orderJPAMapper.findById(orderId).orElse(null);
    }

    @Override
    public List<OrderItem> findOrderItemsByOrderId(UUID orderId) {
        return orderItemJPAMapper.findByOrderItemId_OrderId(orderId);
    }

    @Override
    public void deleteOrderItemsByOrderId(UUID orderId) {
        orderItemJPAMapper.deleteByOrderItemId_OrderId(orderId);
    }

    @Override
    public void deleteOrderById(UUID orderId) {
        orderJPAMapper.deleteById(orderId);
    }

}

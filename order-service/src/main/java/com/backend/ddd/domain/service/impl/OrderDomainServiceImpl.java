package com.backend.ddd.domain.service.impl;

import com.backend.ddd.domain.model.entity.Order;
import com.backend.ddd.domain.model.entity.OrderItem;
import com.backend.ddd.domain.repository.OrderDomainRepository;
import com.backend.ddd.domain.service.OrderDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

package com.backend.ddd.infrastructure.persistence.mapper;

import com.backend.ddd.domain.model.entity.OrderItem;
import com.backend.ddd.domain.model.entity.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface OrderItemJPAMapper extends JpaRepository<OrderItem, OrderItemId> {

    /** Find all order items by orderId */
    List<OrderItem> findByOrderItemId_OrderId(UUID orderId);

    /** Delete all order items by orderId */
    void deleteByOrderItemId_OrderId(UUID orderId);

}

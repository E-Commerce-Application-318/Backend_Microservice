package com.backend.ddd.infrastructure.persistence.mapper;

import com.backend.ddd.domain.model.entity.OrderItem;
import com.backend.ddd.domain.model.entity.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemJPAMapper extends JpaRepository<OrderItem, OrderItemId> {
}

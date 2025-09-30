package com.backend.ddd.infrastructure.persistence.mapper;

import com.backend.ddd.domain.model.entity.OrderItem;
import com.backend.ddd.domain.model.entity.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrderItemJPAMapper extends JpaRepository<OrderItem, OrderItemId> {
    @Query("select ot from OrderItem ot where ot.orderItemId.orderId = :orderId")
    List<OrderItem> findAllByOrderId(@Param("orderId")UUID orderId);
}

package com.backend.ddd.infrastructure.persistence.mapper;

import com.backend.ddd.domain.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrderJPAMapper extends JpaRepository<Order, UUID> {
    @Query("SELECT o FROM Order o WHERE o.userId = :userId")
    List<Order> findOrdersByUserId(@Param("userId") UUID userId);
}

package com.backend.ddd.infrastructure.persistence.mapper;

import com.backend.ddd.domain.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CartJPAMapper extends JpaRepository<Cart, UUID> {
    @Query("select c from Cart c where c.userId = :userId")
    Cart getCartIdByUserId(@Param("userId") UUID userId);

}

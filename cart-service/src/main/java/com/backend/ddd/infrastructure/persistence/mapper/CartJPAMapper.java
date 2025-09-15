package com.backend.ddd.infrastructure.persistence.mapper;

import com.backend.ddd.domain.model.entity.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CartJPAMapper extends JpaRepository<Cart, UUID> {
    @Query("select c from Cart c where c.userId = :userId")
    List<Cart> getCartsByUserId(@Param("userId") UUID userId);

    @Query("select c from Cart c where c.userId = :userId and c.productId = :productId")
    Cart getCartByUserIdAndProductId(@Param("userId") UUID userId, @Param("productId") UUID productId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("delete from Cart c where c.userId = :userId and c.productId = :productId")
    void deleteByUserIdAndProductId(
            @Param("userId") UUID userID,
            @Param("productId") UUID productId
    );
}

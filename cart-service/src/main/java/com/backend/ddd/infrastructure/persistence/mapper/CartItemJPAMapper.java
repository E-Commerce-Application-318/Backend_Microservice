package com.backend.ddd.infrastructure.persistence.mapper;

import com.backend.ddd.domain.model.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CartItemJPAMapper extends JpaRepository<CartItem, UUID> {
    @Query("select ci from CartItem ci where ci.cartId = :cartId")
    List<CartItem> getCartItemByCartId(@Param("cartId") UUID cartId);

    @Query("select ci from CartItem ci where ci.cartId = :cartID and ci.productId = :productID")
    CartItem getCartItemByCartIdAndProductId(
            @Param("cartId") UUID cartId,
            @Param("productId") UUID productId
    );

}

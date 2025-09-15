package com.backend.ddd.domain.repository;

import com.backend.ddd.domain.model.entity.Cart;

import java.util.List;
import java.util.UUID;

public interface CartDomainRepository {
    List<Cart> getCartsByUserId(UUID userId);
    Cart getCartByUserIdAndProductId(UUID userId, UUID productId);
    Cart saveCart(Cart cart);
    void removeCartByUserIdAndProductId(UUID userId, UUID productId);
}

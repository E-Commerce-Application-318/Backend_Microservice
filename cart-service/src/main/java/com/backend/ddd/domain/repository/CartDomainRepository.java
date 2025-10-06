package com.backend.ddd.domain.repository;

import com.backend.ddd.domain.model.entity.Cart;

import java.util.List;
import java.util.UUID;

public interface CartDomainRepository {
    List<Cart> getCartsByUserId(UUID userId);
    Cart getCartByUserIdAndProductId(UUID userId, UUID productId);
    List<Cart> getCartsByCartIds(List<UUID> cartIds);
    Cart saveCart(Cart cart);
    void removeCartsByCartIds(List<UUID> cartIds);
    void removeCartByUserIdAndProductId(UUID userId, UUID productId);
}

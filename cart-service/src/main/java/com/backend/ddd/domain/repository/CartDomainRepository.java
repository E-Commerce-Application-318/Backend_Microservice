package com.backend.ddd.domain.repository;

import com.backend.ddd.domain.model.entity.Cart;
import com.backend.ddd.domain.model.entity.CartItem;

import java.util.List;
import java.util.UUID;

public interface CartDomainRepository {

    Cart getCartByUserId(UUID userId);
    Cart saveCart(Cart cart);

    List<CartItem> getCartItemsByCartId(UUID cartId);
    CartItem saveCartItem(CartItem cartItem);
    CartItem getCartItemByCartIdAndProductId(UUID cartId, UUID productId);
    void removeCartItem(CartItem cartItem);
}

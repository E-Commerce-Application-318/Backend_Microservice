package com.backend.ddd.domain.service;

import com.backend.ddd.domain.model.entity.Cart;
import com.backend.ddd.domain.model.entity.CartItem;

import java.util.List;
import java.util.UUID;

public interface CartDomainService {

    Cart getCartByUserId(UUID userId);
    Cart saveCart(Cart cart);
    
    List<CartItem> getCartItemsByCartId(UUID cartId);
    CartItem getCartItemByCartIdAndProductId(UUID cartId, UUID productId);
    CartItem saveCartItem(CartItem cartItem);
    void removeCartItem(CartItem cartItem);
}

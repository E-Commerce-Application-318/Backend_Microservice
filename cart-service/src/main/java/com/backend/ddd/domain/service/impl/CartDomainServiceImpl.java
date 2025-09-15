package com.backend.ddd.domain.service.impl;

import com.backend.ddd.domain.model.entity.Cart;
import com.backend.ddd.domain.model.entity.CartItem;
import com.backend.ddd.domain.repository.CartDomainRepository;
import com.backend.ddd.domain.service.CartDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartDomainServiceImpl implements CartDomainService {
    @Autowired
    private CartDomainRepository cartDomainRepository;

    public Cart getCartByUserId(UUID userId) {
        return cartDomainRepository.getCartByUserId(userId);
    }

    public Cart saveCart(Cart cart) {
        return cartDomainRepository.saveCart(cart);
    }

    public List<CartItem> getCartItemsByCartId(UUID cartId) {
        return cartDomainRepository.getCartItemsByCartId(cartId);
    }

    public CartItem  getCartItemByCartIdAndProductId(UUID cartId, UUID productId) {
        return cartDomainRepository.getCartItemByCartIdAndProductId(cartId, productId);
    }

    public CartItem saveCartItem(CartItem cartItem) {
        return cartDomainRepository.saveCartItem(cartItem);
    }

    public void removeCartItem(CartItem cartItem) {
        cartDomainRepository.removeCartItem(cartItem);
    }
}

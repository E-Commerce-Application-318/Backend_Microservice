package com.backend.ddd.domain.service.impl;

import com.backend.ddd.domain.model.entity.Cart;
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

    @Override
    public List<Cart> getCartsByUserId(UUID userId) {
        return cartDomainRepository.getCartsByUserId(userId);
    }

    @Override
    public Cart getCartByUserIdAndProductId (UUID userId, UUID productId) {
        return  cartDomainRepository.getCartByUserIdAndProductId(userId, productId);
    }

    @Override
    public List<Cart> getCartsByCartIds(List<UUID> cartIds) {
        return cartDomainRepository.getCartsByCartIds(cartIds);
    }

    @Override
    public Cart saveCart(Cart cart) {
        return cartDomainRepository.saveCart(cart);
    }

    @Override
    public void removeCartByUserIdAndProductId(UUID userId, UUID productId) {
        cartDomainRepository.removeCartByUserIdAndProductId(userId, productId);
    }

}

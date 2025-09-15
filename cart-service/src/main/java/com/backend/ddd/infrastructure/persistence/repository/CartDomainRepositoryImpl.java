package com.backend.ddd.infrastructure.persistence.repository;

import com.backend.ddd.domain.model.entity.Cart;
import com.backend.ddd.domain.repository.CartDomainRepository;
import com.backend.ddd.infrastructure.persistence.mapper.CartJPAMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class CartDomainRepositoryImpl implements CartDomainRepository {
    @Autowired
    private CartJPAMapper cartJPAMapper;

    @Override
    public List<Cart> getCartsByUserId(UUID userId) {
        return cartJPAMapper.getCartsByUserId(userId);
    }

    @Override
    public Cart getCartByUserIdAndProductId(UUID userId, UUID productId) {
        return  cartJPAMapper.getCartByUserIdAndProductId(userId, productId);
    }

    @Override
    public Cart saveCart(Cart cart) {
        return cartJPAMapper.save(cart);
    }

    @Override
    public void removeCartByUserIdAndProductId(UUID userId, UUID productId) {
        cartJPAMapper.deleteByUserIdAndProductId(userId, productId);
    }
}

package com.backend.ddd.infrastructure.persistence.repository;

import com.backend.ddd.domain.model.entity.Cart;
import com.backend.ddd.domain.model.entity.CartItem;
import com.backend.ddd.domain.repository.CartDomainRepository;
import com.backend.ddd.infrastructure.persistence.mapper.CartItemJPAMapper;
import com.backend.ddd.infrastructure.persistence.mapper.CartJPAMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class CartDomainRepositoryImpl implements CartDomainRepository {
    @Autowired
    private CartJPAMapper cartJPAMapper;

    @Autowired
    private CartItemJPAMapper cartItemJPAMapper;

    public Cart getCartByUserId(UUID userId) {
        return cartJPAMapper.getCartIdByUserId(userId);
    }

    public Cart saveCart(Cart cart) {
        return cartJPAMapper.save(cart);
    }

    public List<CartItem> getCartItemsByCartId(UUID cartId) {
        return cartItemJPAMapper.getCartItemByCartId(cartId);
    }

    public CartItem getCartItemByCartIdAndProductId(UUID cartId, UUID productId) {
        return cartItemJPAMapper.getCartItemByCartIdAndProductId(cartId, productId);
    }
    public CartItem saveCartItem(CartItem cartItem) {
        return cartItemJPAMapper.save(cartItem);
    }

    public void removeCartItem(CartItem cartItem) {
        cartItemJPAMapper.delete(cartItem);
    }

}

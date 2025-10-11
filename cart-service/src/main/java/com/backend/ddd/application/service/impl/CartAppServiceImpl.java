package com.backend.ddd.application.service.impl;

import com.backend.ddd.application.mapper.CartAppMapper;
import com.backend.ddd.application.service.CartAppService;
import com.backend.ddd.controller.model.dto.CartBasketResponseDTO;
import com.backend.ddd.controller.model.dto.CartResponseDTO;
import com.backend.ddd.domain.model.entity.Cart;
import com.backend.ddd.domain.service.CartDomainService;
import com.backend.ddd.infrastructure.persistence.client.ProductClient;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartAppServiceImpl implements CartAppService {

    @Autowired
    private CartDomainService cartDomainService;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private CartAppMapper cartAppMapper;


    @Override
    public CartBasketResponseDTO getAllProductsByUserId(UUID userId) {
        try {
            List<Cart> carts = cartDomainService.getCartsByUserId(userId);
            return cartAppMapper.convertToCartBasketResponseDTO(carts);
        } catch (Exception e) {
            log.error("Error in getAllProductsByUserId: ", e);
            return null;
        }
    }

    @Override
    public Map<UUID, Integer> getProductIdsAndQuantitiesByCartIds(List<UUID> cartIds) {
        List<Cart> carts = cartDomainService.getCartsByCartIds(cartIds);
        return carts.stream()
                .collect(Collectors.toMap(Cart::getProductId, Cart::getQuantity));
    }

    @Override
    public List<CartResponseDTO> getCartsByCartIds(List<UUID> cartIds) {
        List<Cart> carts = cartDomainService.getCartsByCartIds(cartIds);
        return cartAppMapper.cartsToCartResponseDTOs(carts);
    }

    @Override
    public CartResponseDTO addProductToCart(UUID userId, UUID productId, Integer quantity) {
        Cart existCart =  cartDomainService.getCartByUserIdAndProductId(userId, productId);
        if (existCart != null) {
            return updateCart(userId, productId, quantity);
        }
        ExternalProduct externalProduct = productClient.getProductById(productId);
        Cart cart = new Cart(
                userId,
                externalProduct.getId(),
                externalProduct.getName(),
                externalProduct.getBrand(),
                externalProduct.getPrice(),
                quantity);
        Cart savedCart = cartDomainService.saveCart(cart);
        return cartAppMapper.cartToCartResponseDTO(savedCart);
    }

    @Override
    public CartResponseDTO updateCart(UUID userId, UUID productId, Integer quantity) {
        Cart cart = cartDomainService.getCartByUserIdAndProductId(userId, productId);
        cart.setQuantity(cart.getQuantity() + quantity);
        if (cart.getQuantity() <= 0) {
            cart.setQuantity(1);
        }
        try {
            Cart updatedCart = cartDomainService.saveCart(cart);
            return cartAppMapper.cartToCartResponseDTO(updatedCart);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean removeCartsByCartIds(List<UUID> cartIds) {
        try {
            cartDomainService.removeCartsByCartIds(cartIds);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean removeProductFromCart(UUID userId, UUID productId) {
        try {
            cartDomainService.removeCartByUserIdAndProductId(userId, productId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

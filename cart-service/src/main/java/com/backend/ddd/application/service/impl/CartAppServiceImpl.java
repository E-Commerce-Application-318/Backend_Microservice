package com.backend.ddd.application.service.impl;

import com.backend.ddd.application.mapper.CartMapper;
import com.backend.ddd.application.service.CartAppService;
import com.backend.ddd.controller.model.dto.CartResponseDTO;
import com.backend.ddd.domain.model.entity.Cart;
import com.backend.ddd.domain.service.CartDomainService;
import com.backend.ddd.infrastructure.persistence.client.ShopClient;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalProduct;
import com.backend.ddd.infrastructure.persistence.client.ProductClient;
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
    private ShopClient shopClient;

    private static final CartMapper cartMapper = new CartMapper();

    public CartResponseDTO getAllProductsByUserId(UUID userId) {
        List<Cart> carts = cartDomainService.getCartsByUserId(userId);
        List<UUID> productIds = carts.stream()
                .map(Cart::getProductId)
                .collect(Collectors.toList());
        List<ExternalProduct> externalProducts = productClient.getProductsByProductIds(productIds);

        return cartMapper.convertToCartResponseDTO(carts, externalProducts);
    }

    @Override
    public Map<UUID, Integer> getProductIdsAndQuantitiesByCartIds(List<UUID> cartIds) {
        List<Cart> carts = cartDomainService.getCartsByCartIds(cartIds);
        return carts.stream()
                .collect(Collectors.toMap(Cart::getProductId, Cart::getQuantity));
    }

    public Boolean addProductToCart(UUID userId, UUID productId, Integer quantity) {
        Cart existCart =  cartDomainService.getCartByUserIdAndProductId(userId, productId);
        if (existCart != null) {
            return updateCart(userId, productId, quantity);
        }
        Cart cart = new Cart().setUserId(userId).setProductId(productId).setQuantity(quantity);
        Cart savedCart = cartDomainService.saveCart(cart);
        return savedCart != null;
    }

    public Boolean updateCart(UUID userId, UUID productId, Integer quantity) {
        Cart cart = cartDomainService.getCartByUserIdAndProductId(userId, productId);
        Integer updatedQuantity = cart.getQuantity() + quantity;
        if (updatedQuantity <= 0) {
            removeProductFromCart(userId, productId);
            return true;
        }
        cart.setQuantity(updatedQuantity);
        try {
            cartDomainService.saveCart(cart);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean removeProductFromCart(UUID userId, UUID productId) {
        try {
            cartDomainService.removeCartByUserIdAndProductId(userId, productId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

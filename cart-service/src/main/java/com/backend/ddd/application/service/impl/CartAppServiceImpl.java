package com.backend.ddd.application.service.impl;

import com.backend.ddd.application.mapper.CartMapper;
import com.backend.ddd.application.service.CartAppService;
import com.backend.ddd.controller.model.dto.CartResponseDTO;
import com.backend.ddd.domain.model.entity.Cart;
import com.backend.ddd.domain.model.entity.CartItem;
import com.backend.ddd.domain.service.CartDomainService;
import com.backend.ddd.infrastructure.persistence.client.ShopClient;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalProduct;
import com.backend.ddd.infrastructure.persistence.client.ProductClient;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalShop;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    private Cart getCartByUserId(UUID userId) {
        Cart cart = cartDomainService.getCartByUserId(userId);
        return cart;
    }

    private List<CartItem> getCartItemsByUserId(UUID cardId) {
        List<CartItem> cartItemList = cartDomainService.getCartItemsByCartId(cardId);
        return cartItemList;
    }

    public CartResponseDTO getCartDetailByUserId(UUID userId) {
        Cart cart = getCartByUserId(userId);
        List<CartItem> cartItemList = getCartItemsByUserId(cart.getId());
        log.info("Cart: " + cart);
        log.info("List cart items: " + cartItemList);
        return cartMapper.convertToCartResponseDTO(cart, cartItemList);
    }

    public Boolean addProductToCart(UUID userId, UUID productId, Integer quantity) {
        Cart cart = getCartByUserId(userId);


        // get product information and shop information by calling product-service and shop-service API
        ExternalProduct externalProduct = productClient.getProductById(productId);
        ExternalShop externalShop = shopClient.getShopDetailByShopId(externalProduct.getShopId());

        // convert product information and cart information into CartItem
        CartItem cartItem = cartMapper.externalProductToCartItem(cart.getId(), externalShop.getName(), quantity, externalProduct);

        // save new item added to CartItem
        CartItem savedCartItem =  cartDomainService.saveCartItem(cartItem);

        // update Cart total_item and total_amount
        Integer newQuantity = cart.getTotalItem() + quantity;
        Double newTotalAmount = cart.getTotalAmount() + quantity * externalProduct.getPrice();
        cart.setTotalItem(newQuantity).setTotalAmount(newTotalAmount);
        Cart updatedCart = cartDomainService.saveCart(cart);
        return updatedCart != null;
    }

//    public void updateProductInCart(UUID userId, UUID productId, Integer quantity) {
//        Cart cart = getCartByUserId(userId);
//
//        CartItem cartItem = cartDomainService.getCartItemByCartIdAndProductId(cart.getId(), productId);
//
////        Car
//        cartItem.setQuantity(quantity);
//
//        cartDomainService.
//    }

    public void removeProductFromCart(UUID userId, UUID productId) {
        Cart cart = getCartByUserId(userId);

        CartItem cartItem = cartDomainService.getCartItemByCartIdAndProductId(cart.getId(), productId);

        cartDomainService.removeCartItem(cartItem);
    }

}

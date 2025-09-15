package com.backend.ddd.application.mapper;

import com.backend.ddd.controller.model.dto.CartResponseDTO;
import com.backend.ddd.controller.model.dto.ProductResponseDTO;
import com.backend.ddd.domain.model.entity.Cart;
import com.backend.ddd.domain.model.entity.CartItem;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalProduct;

import java.util.List;
import java.util.UUID;

public class CartMapper {
    private ProductResponseDTO cartItemToProductResponseDTO(CartItem cartItem) {
        return new ProductResponseDTO()
                .setId(cartItem.getId())
                .setName(cartItem.getProductName())
                .setPrice(cartItem.getUnitPrice())
                .setShopName(cartItem.getShopName())
                .setBrand(cartItem.getBrand())
                .setQuantity(cartItem.getQuantity());
    }
    private List<ProductResponseDTO> cartItemListToProductResponseDTOList(List<CartItem> cartItemList) {
        return cartItemList.stream()
                .map(this::cartItemToProductResponseDTO)
                .toList();
    }

    public CartResponseDTO convertToCartResponseDTO (Cart cart, List<CartItem> cartItemList) {
        return new CartResponseDTO()
                .setId(cart.getId())
                .setTotalItem(cart.getTotalItem())
                .setTotalAmount(cart.getTotalAmount())
                .setProductReponseDTOList(this.cartItemListToProductResponseDTOList(cartItemList));
    }

    public CartItem externalProductToCartItem(UUID cardId, String shopName, Integer quantity, ExternalProduct externalProduct) {
        return new CartItem()
                .setCartId(cardId)
                .setProductId(externalProduct.getId())
                .setProductName(externalProduct.getName())
                .setBrand(externalProduct.getBrand())
                .setShopName(shopName)
                .setQuantity(quantity)
                .setUnitPrice(externalProduct.getPrice());
    }
}

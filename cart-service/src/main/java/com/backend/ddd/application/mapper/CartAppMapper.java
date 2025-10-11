package com.backend.ddd.application.mapper;

import com.backend.ddd.controller.model.dto.CartBasketResponseDTO;
import com.backend.ddd.controller.model.dto.CartResponseDTO;
import com.backend.ddd.controller.model.dto.ProductResponseDTO;
import com.backend.ddd.domain.model.entity.Cart;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalProduct;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CartAppMapper {
    public CartResponseDTO cartToCartResponseDTO(Cart cart) {
        return new CartResponseDTO(
                cart.getId(),
                cart.getProductId(),
                cart.getProductName(),
                cart.getBrand(),
                cart.getPrice(),
                cart.getQuantity()
                );
    }
    public List<CartResponseDTO> cartsToCartResponseDTOs(List<Cart> carts) {
        return carts.stream()
                .map(this::cartToCartResponseDTO)
                .toList();
    }

    public CartBasketResponseDTO convertToCartBasketResponseDTO(List<Cart> carts) {
        Integer totalItem = carts.stream()
                .mapToInt(Cart::getQuantity)
                .sum();
        Double totalPrice = carts.stream()
                .mapToDouble(cart -> cart.getQuantity() * cart.getPrice())
                .sum();

        return new CartBasketResponseDTO()
                .setTotalItem(totalItem)
                .setTotalAmount(totalPrice)
                .setCartResponseDTOs(cartsToCartResponseDTOs(carts));
    }
}

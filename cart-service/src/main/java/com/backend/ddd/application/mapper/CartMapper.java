package com.backend.ddd.application.mapper;

import com.backend.ddd.controller.model.dto.CartResponseDTO;
import com.backend.ddd.controller.model.dto.ProductResponseDTO;
import com.backend.ddd.domain.model.entity.Cart;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalProduct;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class CartMapper {
    private ProductResponseDTO externalProductToProductResponseDTO(ExternalProduct externalProduct, Integer quantity) {
        return new ProductResponseDTO()
                .setId(externalProduct.getId())
                .setName(externalProduct.getName())
                .setPrice(externalProduct.getPrice())
                .setBrand(externalProduct.getBrand())
                .setCartQuantity(quantity);
    }
    private List<ProductResponseDTO> externalProductsToProductResponseDTOs(
            List<ExternalProduct> externalProducts,
            List<Cart> carts
    ) {
        Map<UUID, Integer> quantityByProduct = carts.stream()
                .collect(Collectors.toMap(Cart::getProductId, Cart::getQuantity));

        return externalProducts.stream()
                .map(p -> externalProductToProductResponseDTO(p, quantityByProduct.get(p.getId())))
                .toList();
    }

    public CartResponseDTO convertToCartResponseDTO(List<Cart> carts, List<ExternalProduct> externalProducts) {
        Integer totalItem = carts.stream()
                .mapToInt(Cart::getQuantity)
                .sum();
        Double totalPrice = carts.stream()
                .mapToDouble(cart -> {
                    ExternalProduct product = externalProducts.stream()
                            .filter(p -> p.getId().equals(cart.getProductId()))
                            .findFirst()
                            .orElse(null);
                    if (product != null) {
                        return product.getPrice() * cart.getQuantity();
                    }
                    return 0.0; // If product not found for this productId
                })
                .sum();
        return new CartResponseDTO()
                .setTotalItem(totalItem)
                .setTotalAmount(totalPrice)
                .setProductReponseDTOs(this.externalProductsToProductResponseDTOs(externalProducts, carts));
    }
}

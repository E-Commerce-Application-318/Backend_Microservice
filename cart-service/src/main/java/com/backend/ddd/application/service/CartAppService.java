package com.backend.ddd.application.service;

import com.backend.ddd.controller.model.dto.CartResponseDTO;

import java.util.UUID;

public interface CartAppService {
    CartResponseDTO getAllProductsByUserId(UUID userId);
    Boolean addProductToCart(UUID userId, UUID productId, Integer quantity);
    Boolean updateCart(UUID userId, UUID productId, Integer quantity);
    Boolean removeProductFromCart(UUID userId, UUID productId);
}

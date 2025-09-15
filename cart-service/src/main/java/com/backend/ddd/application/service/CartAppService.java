package com.backend.ddd.application.service;

import com.backend.ddd.controller.model.dto.CartResponseDTO;

import java.util.UUID;

public interface CartAppService {
    CartResponseDTO getCartDetailByUserId(UUID userId);
    Boolean addProductToCart(UUID userId, UUID productId, Integer quantity);
    void removeProductFromCart(UUID userId, UUID productId);
}

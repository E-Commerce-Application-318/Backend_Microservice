package com.backend.ddd.application.service;

import com.backend.ddd.controller.model.dto.CartResponseDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CartAppService {
    CartResponseDTO getAllProductsByUserId(UUID userId);
    Map<UUID, Integer> getProductIdsAndQuantitiesByCartIds(List<UUID> cartIds);
    Boolean addProductToCart(UUID userId, UUID productId, Integer quantity);
    Boolean updateCart(UUID userId, UUID productId, Integer quantity);
    Boolean removeCartsByCartIds(List<UUID> cartIds);
    Boolean removeProductFromCart(UUID userId, UUID productId);
}

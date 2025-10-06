package com.backend.ddd.controller.resource;

import com.backend.ddd.application.service.CartAppService;
import com.backend.ddd.controller.model.dto.ApiResponseDTO;
import com.backend.ddd.controller.model.dto.CartRequestDTO;
import com.backend.ddd.controller.model.dto.CartResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
@Slf4j
public class CartController {

    @Autowired
    private CartAppService cartAppService;

    @GetMapping("/{userId}/get-all-items")
    public ResponseEntity<ApiResponseDTO<CartResponseDTO>> getAllCartItemsByUserId(
            @PathVariable("userId") UUID userId
    ) {
        CartResponseDTO cartResponseDTO = cartAppService.getAllProductsByUserId(userId);

        if  (cartResponseDTO == null) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error("Not found any products in cart"));
        }
        return ResponseEntity.ok().body(ApiResponseDTO.success("Found products", cartResponseDTO));
    }

    @PostMapping("/get-productids-quantity-by-cart-ids")
    public ResponseEntity<ApiResponseDTO<Map<UUID, Integer>>> getProductIdsAndQuantityByCartIds(
            @RequestBody List<UUID> cartIds
    ) {
        Map<UUID, Integer> productIdsAndQuantity = cartAppService.getProductIdsAndQuantitiesByCartIds(cartIds);
        if (productIdsAndQuantity == null || productIdsAndQuantity.isEmpty()) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error("Not found any products in cart"));
        }
        return ResponseEntity.ok().body(ApiResponseDTO.success("Found products", productIdsAndQuantity));
    }

    @PostMapping("/{userId}/add-item")
    public ResponseEntity<ApiResponseDTO<Boolean>> addProductToCart(
            @PathVariable("userId") UUID userId,
            @RequestBody CartRequestDTO cartRequestDTO
            ) {
        Boolean success = cartAppService.addProductToCart(userId, cartRequestDTO.getProductId(), cartRequestDTO.getQuantity());
        if (!success)
            return ResponseEntity.badRequest().body(ApiResponseDTO.error("Failed to add product"));
        return ResponseEntity.ok().body(ApiResponseDTO.success("Add product successfully", true));
    }

    @PutMapping("/{userId}/update-product")
    public ResponseEntity<ApiResponseDTO<Boolean>> updateCartItem(
        @PathVariable("userId") UUID userId,
        @RequestBody CartRequestDTO cartRequestDTO
    ) {
        if (cartAppService.updateCart(userId, cartRequestDTO.getProductId(), cartRequestDTO.getQuantity()))
            return ResponseEntity.ok().body(ApiResponseDTO.success("Updated product successfully", true));
        else
            return ResponseEntity.badRequest().body(ApiResponseDTO.error("Failed to update product"));
    }

    @DeleteMapping("/remove-carts")
    public ResponseEntity<ApiResponseDTO<Boolean>> removeCartsByCartIds(
            @RequestBody List<UUID> cartIds
    ) {
        if (cartAppService.removeCartsByCartIds(cartIds)) {
            return ResponseEntity.ok().body(ApiResponseDTO.success("Removed carts successfully", true));
        }
        return ResponseEntity.badRequest().body(ApiResponseDTO.error("Failed to remove carts"));
    }

    @DeleteMapping("/{userId}/remove-product")
    public ResponseEntity<ApiResponseDTO<Boolean>> removeProductFromCart(
            @PathVariable("userId") UUID userId,
            @RequestBody UUID productId
    ) {
        if (cartAppService.removeProductFromCart(userId, productId)) {
            return ResponseEntity.ok().body(ApiResponseDTO.success("Removed product successfully", true));
        }
        return ResponseEntity.badRequest().body(ApiResponseDTO.error("Failed to remove product"));
    }
}



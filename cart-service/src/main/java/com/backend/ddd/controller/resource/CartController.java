package com.backend.ddd.controller.resource;

import com.backend.ddd.application.service.CartAppService;
import com.backend.ddd.controller.model.dto.ApiResponseDTO;
import com.backend.ddd.controller.model.dto.CartRequestDTO;
import com.backend.ddd.controller.model.dto.CartResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cart/{userId}")
@Slf4j
public class CartController {

    @Autowired
    private CartAppService cartAppService;

    @GetMapping("/get-cart-items")
    public ResponseEntity<ApiResponseDTO<CartResponseDTO>> getAllCartItemsByUserId(
            @PathVariable("userId") UUID userId
    ) {
        CartResponseDTO cartResponseDTO = cartAppService.getCartDetailByUserId(userId);

        if  (cartResponseDTO == null) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error("Not found any products in cart"));
        }
        return ResponseEntity.ok().body(ApiResponseDTO.success("Found products", cartResponseDTO));
    }

    @PostMapping("/add-item")
    public ResponseEntity<ApiResponseDTO<Boolean>> addProductToCart(
            @PathVariable("userId") UUID userId,
            @RequestBody CartRequestDTO cartRequestDTO
            ) {
        Boolean success = cartAppService.addProductToCart(userId, cartRequestDTO.getProductId(), cartRequestDTO.getQuantity());
        if (!success)
            return ResponseEntity.badRequest().body(ApiResponseDTO.error("Failed to add product"));
        return ResponseEntity.ok().body(ApiResponseDTO.success("Add product successfully", true));
    }

//    @PutMapping
//    public ResponseEntity<ApiResponseDTO<Boolean>> updateCartItem(
//        @PathVariable("userId") UUID userId,
//        @RequestBody CartRequestDTO cartRequestDTO
//    ) {
//        Boolean success = cartAppService.
//    }

    @DeleteMapping("/remove-product/{productId}")
    public ResponseEntity<ApiResponseDTO<Boolean>> removeProductFromCart(
            @PathVariable("userId") UUID userId,
            @RequestBody UUID productId
    ) {
        try {
            cartAppService.removeProductFromCart(userId, productId);
            return ResponseEntity.ok().body(ApiResponseDTO.success("Remove product successfully", true));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error("Failed to remove product"));
        }
    }
//    @PutMapping("/update-product/{productId}")

}



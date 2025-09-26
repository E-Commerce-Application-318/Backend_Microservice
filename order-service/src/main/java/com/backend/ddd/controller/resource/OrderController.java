package com.backend.ddd.controller.resource;

import com.backend.ddd.application.service.OrderAppService;
import com.backend.ddd.controller.model.dto.ApiResponseDTO;
import com.backend.ddd.controller.model.dto.OrderResponseDTO;
import com.backend.ddd.controller.model.dto.PaymentRequestDTO;
import com.backend.ddd.controller.model.dto.UpdateOrderRequestDTO;
import com.backend.ddd.domain.model.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderAppService orderAppService;

    @GetMapping("/{userId}/get-all-orders")
    public ResponseEntity<ApiResponseDTO<Order>> getOrders(
            @PathVariable("userId") String userId
    ) {
        return null;
    }

    @PostMapping("/{userId}/create-order")
    public ResponseEntity<ApiResponseDTO<OrderResponseDTO>> createOrder(
            @PathVariable("userId") UUID userId,
            @RequestBody List<UUID> cartIds
    ) {
        log.info("Check create order for userId: {}, cartIds: {}", userId, cartIds);
        try {
            OrderResponseDTO orderResponseDTO = orderAppService.createOrder(userId, cartIds);
            if (orderResponseDTO != null) {
                return ResponseEntity.ok(ApiResponseDTO.success("Create order successfully", orderResponseDTO));
            } else {
                return ResponseEntity.badRequest().body(ApiResponseDTO.error("Failed to create order."));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error(e.getMessage()));
        }
    }

    @PutMapping("/update-order")
    public ResponseEntity<ApiResponseDTO<String>> updateOrder(
            @RequestBody UpdateOrderRequestDTO orderUpdateRequestDTO
    ) {
        String message = orderAppService.updateOrderAddressPhoneNumber(orderUpdateRequestDTO.getOrderId(), orderUpdateRequestDTO.getAddress(), orderUpdateRequestDTO.getPhoneNumber());
        if (!message.equals("Updated successfully")) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error(message));
        }
        return ResponseEntity.ok(ApiResponseDTO.success("Updated Successfully", message));
    }

    @PutMapping("/payment")
    public ResponseEntity<ApiResponseDTO<String>> paymentOrder(
            @RequestBody PaymentRequestDTO orderUpdateRequestDTO
    ) {

    }
}

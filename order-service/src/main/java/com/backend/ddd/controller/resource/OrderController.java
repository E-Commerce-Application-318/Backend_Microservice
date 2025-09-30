package com.backend.ddd.controller.resource;

import com.backend.ddd.application.service.OrderAppService;
import com.backend.ddd.controller.model.dto.ApiResponseDTO;
import com.backend.ddd.controller.model.dto.OrderResponseDTO;
import com.backend.ddd.controller.model.dto.PaymentRequestDTO;
import com.backend.ddd.controller.model.dto.UpdateOrderRequestDTO;
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
    public ResponseEntity<ApiResponseDTO<List<OrderResponseDTO>>> getOrders(
            @PathVariable("userId") UUID userId
    ) {
        try {
            List<OrderResponseDTO> orderResponseDTOs = orderAppService.getAllOrdersByUserId(userId);
            if (orderResponseDTOs.isEmpty()) {
                return ResponseEntity.ok().body(ApiResponseDTO.success("User does not have any order", orderResponseDTOs));
            }
            return ResponseEntity.ok().body(ApiResponseDTO.success("Found " + orderResponseDTOs.toArray().length + " order(s)", orderResponseDTOs));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error(e.getMessage()));
        }
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
            @RequestParam("orderId") UUID orderId,
            @RequestParam("userId") UUID userId,
            @RequestBody PaymentRequestDTO orderUpdateRequestDTO
    ) {
        String paymentResult = orderAppService.processPayment(orderId, userId, orderUpdateRequestDTO);
        if (paymentResult.equals("Payment process successfully"))
            return ResponseEntity.ok().body(ApiResponseDTO.success("Payment process successfully", paymentResult));

        return ResponseEntity.badRequest().body(ApiResponseDTO.error(paymentResult));
    }
}

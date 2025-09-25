package com.backend.ddd.controller.resource;

import com.backend.ddd.application.service.OrderAppService;
import com.backend.ddd.controller.model.dto.ApiResponseDTO;
import com.backend.ddd.controller.model.dto.OrderResponseDTO;
import com.backend.ddd.domain.model.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/order/{userId}")
public class OrderController {

    @Autowired
    private OrderAppService orderAppService;

    @GetMapping("/get-all-orders")
    public ResponseEntity<ApiResponseDTO<Order>> getOrders(
            @PathVariable("userId") String userId
    ) {
        return null;
    }

    @PostMapping("/create-order")
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

    @PostMapping("/update-order")
    public Boolean updateOrder(@PathVariable("userId") String userId) {
        return true;
    }

    /** DELETE endpoint to remove an order and refund stock */
    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<ApiResponseDTO<Boolean>> deleteOrder(
            @PathVariable("orderId") UUID orderId
    ) {
        try {
            boolean ok = orderAppService.deleteOrder(orderId);
            if (ok) {
                return ResponseEntity.ok(ApiResponseDTO.success("Delete order & refund inventory successfully", true));
            }
            return ResponseEntity.badRequest().body(ApiResponseDTO.error("Failed to delete order"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error(e.getMessage()));
        }
    }

}

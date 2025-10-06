package com.backend.shared.domain.order_product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {
    private UUID userId;
    private Double totalAmount;
    private List<OrderItemEvent> orderItemEvents;
    private List<UUID> cartIds; // Optional, used when creating order from cart to remove the carts
}

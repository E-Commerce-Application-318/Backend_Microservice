package com.backend.shared.domain.order_product;

import lombok.Data;

import java.util.List;

@Data
public class OrderCancelledEvent {
    private List<OrderItemEvent> orderItemEvents;
}

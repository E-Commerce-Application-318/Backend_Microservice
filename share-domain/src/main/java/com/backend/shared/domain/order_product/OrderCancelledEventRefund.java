package com.backend.shared.domain.order_product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCancelledEventRefund {
    private UUID userId;
    private Double totalAmount;
}

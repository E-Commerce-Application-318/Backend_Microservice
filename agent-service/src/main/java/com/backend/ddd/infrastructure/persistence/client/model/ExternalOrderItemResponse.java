package com.backend.ddd.infrastructure.persistence.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalOrderItemResponse {
    private UUID orderId;
    private UUID productId;
    private String productName;
    private String brand;
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;
}

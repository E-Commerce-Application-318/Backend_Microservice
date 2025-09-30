package com.backend.ddd.infrastructure.persistence.client.model;

import java.util.UUID;

public class ExternalOrderItemResponse {
    private UUID orderId;
    private UUID productId;
    private String quantity;
    private Double unitPrice;
    private Double totalPrice;
}

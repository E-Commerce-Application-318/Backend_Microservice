package com.backend.ddd.infrastructure.persistence.client.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ExternalOrderResponse {
    private UUID id;
    private UUID userId;
    private Double totalAmount;
    private String shippingAddress;
    private String phoneNumber;
    private List<ExternalOrderItemResponse> externalOrderItemResponses;
}

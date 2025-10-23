package com.backend.ddd.infrastructure.persistence.client.model;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class ExternalOrderResponse {
    private UUID id;
    private Double totalAmount;
    private String status;
    private String userFullName;
    private String shippingAddress;
    private String phoneNumber;
    private Date createdAt;
    private List<ExternalOrderItemResponse> externalOrderItemResponses;
}

package com.backend.ddd.infrastructure.persistence.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalOrderResponse {
    private UUID id;
    private Double totalAmount;
    private String status;
    private String userFullName;
    private String shippingAddress;
    private String phoneNumber;
    private Date createdAt;
    List<ExternalOrderItemResponse> externalOrderItems;
}

package com.backend.ddd.infrastructure.persistence.client.model;

import lombok.Data;

import java.util.UUID;

@Data
public class ExternalCartResponse {
    private UUID id;
    private UUID productId;
    private String productName;
    private String brand;
    private Double price;
    private Integer quantity;
}

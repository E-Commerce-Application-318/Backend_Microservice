package com.backend.ddd.infrastructure.persistence.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalCartResponse {
    private UUID id;
    private UUID userId;
    private UUID productId;
    private String productName;
    private String brand;
    private Double price;
    private Integer quantity;
}
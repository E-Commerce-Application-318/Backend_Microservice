package com.backend.ddd.controller.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDTO {
    private UUID id;
    private UUID productId;
    private String productName;
    private String brand;
    private Double price;
    private Integer quantity;
}

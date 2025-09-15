package com.backend.ddd.controller.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class ProductResponseDTO {
    private UUID id;
    private String name;
    private String brand;
    private String shopName;
    private Double price;
    private Integer quantity;
}

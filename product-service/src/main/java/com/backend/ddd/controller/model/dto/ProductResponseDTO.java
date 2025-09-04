package com.backend.ddd.controller.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class ProductResponseDTO {
    private UUID id;
    private UUID shopId;
    private String name;
    private String description;
    private String brand;
    private Double price;
    private Integer stockNumber;
}

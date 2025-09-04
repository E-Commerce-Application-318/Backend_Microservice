package com.backend.ddd.controller.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain=true)
public class ProductRequestDTO {
    private UUID shopId;
    private String name;
    private String description;
    private String brand;
    private Double price;
    private Integer stockNumber;
}

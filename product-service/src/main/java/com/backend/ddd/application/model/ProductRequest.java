package com.backend.ddd.application.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain=true)
public class ProductRequest {
    private UUID shopId;
    private String name;
    private String description;
    private String brand;
    private Double price;
    private Integer stockNumber;
}

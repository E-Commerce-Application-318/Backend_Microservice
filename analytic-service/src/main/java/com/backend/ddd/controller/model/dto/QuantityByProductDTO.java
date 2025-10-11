package com.backend.ddd.controller.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantityByProductDTO {
    private String productId;
    private String productName;
    private Integer quantity;
    private Instant windowStart;
    private Instant windowEnd;

}

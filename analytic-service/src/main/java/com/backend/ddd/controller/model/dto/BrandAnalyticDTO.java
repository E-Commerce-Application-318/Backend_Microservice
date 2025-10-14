package com.backend.ddd.controller.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandAnalyticDTO {
    private String brand;
    private String data;
    private Instant windowStart;
    private Instant windowEnd;
}

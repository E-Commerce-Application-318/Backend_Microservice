package com.backend.ddd.controller.model.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain=true)
public class ProductRequestDTO {
    @Column(name = "id")
    private UUID id;
    private Integer quantity;
}


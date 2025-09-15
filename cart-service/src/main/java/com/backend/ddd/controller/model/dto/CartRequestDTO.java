package com.backend.ddd.controller.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors
public class CartRequestDTO {
    private UUID productId;
    private int quantity;
}

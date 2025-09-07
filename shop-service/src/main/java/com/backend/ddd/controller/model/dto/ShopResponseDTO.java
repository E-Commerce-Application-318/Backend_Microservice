package com.backend.ddd.controller.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class ShopResponseDTO {
    private UUID id;
    private String name;
    private String address;
    private String description;
}

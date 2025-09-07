package com.backend.ddd.application.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain=true)
public class ShopResponse {
    private UUID id;
    private String name;
    private String address;
    private String description;
}

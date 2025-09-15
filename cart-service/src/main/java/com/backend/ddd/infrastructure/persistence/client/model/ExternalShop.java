package com.backend.ddd.infrastructure.persistence.client.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain=true)
public class ExternalShop {
    private UUID id;
    private String name;
    private String address;
    private String description;
}

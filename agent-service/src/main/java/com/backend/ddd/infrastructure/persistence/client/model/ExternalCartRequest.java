package com.backend.ddd.infrastructure.persistence.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalCartRequest {
    private UUID productId;
    private Integer quantity;
}

package com.backend.ddd.domain.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemId implements Serializable {
    @Column(name = "order_id", columnDefinition = "binary(16)")
    private UUID orderId;

    @Column(name = "product_id", columnDefinition = "binary(16)")
    private UUID productId;
}

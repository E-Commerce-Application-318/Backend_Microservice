package com.backend.ddd.domain.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Data
@Entity
@Table(name = "order_items")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @EmbeddedId
    private OrderItemId orderItemId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "total_price")
    private Double totalPrice;
}

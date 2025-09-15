package com.backend.ddd.domain.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "bianry(16)")
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "total_item")
    private Integer totalItem;

    @Column(name = "total_amount")
    private Double totalAmount;
}

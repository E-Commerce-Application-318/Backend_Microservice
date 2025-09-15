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
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;

    @Column(name = "cart_id", columnDefinition = "binary(16)")
    private UUID cartId;

    @Column(name = "product_id", columnDefinition = "binary(16)")
    private UUID productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "brand")
    private String brand;

    @Column(name = "shop_name")
    private String shopName;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

}

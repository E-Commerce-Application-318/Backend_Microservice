package com.backend.ddd.domain.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Entity
@Table(name="products")
@Accessors(chain=true)
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name="shop_id")
    private UUID shopId;

    private String name;

    private String description;

    private String brand;

    private Double price;

    @Column(name = "stock_number")
    private Integer stockNumber;
}

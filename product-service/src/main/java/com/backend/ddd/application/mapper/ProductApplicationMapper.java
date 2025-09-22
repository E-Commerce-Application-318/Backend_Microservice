package com.backend.ddd.application.mapper;

import com.backend.ddd.controller.model.dto.ProductResponseDTO;
import com.backend.ddd.domain.model.entity.Product;

import java.util.List;

public class ProductApplicationMapper {

    public ProductResponseDTO productToProductResponseDTO(Product product) {
        return new ProductResponseDTO()
                .setId(product.getId())
                .setShopId(product.getShopId())
                .setName(product.getName())
                .setDescription(product.getDescription())
                .setBrand(product.getBrand())
                .setPrice(product.getPrice())
                .setStockNumber(product.getStockNumber());
    }

    public List<ProductResponseDTO> productsToProductResponseDTOs(List<Product> products) {
        if (products.isEmpty()) {
            return List.of();
        }
        return products.stream()
                .map(this::productToProductResponseDTO)
                .toList();
    }
}

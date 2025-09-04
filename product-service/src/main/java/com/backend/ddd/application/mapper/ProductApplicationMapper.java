package com.backend.ddd.application.mapper;

import com.backend.ddd.application.model.ProductResponse;
import com.backend.ddd.domain.model.entity.Product;

import java.util.List;

public class ProductApplicationMapper {
    public ProductResponse productToProductResponse(Product product) {
        return new ProductResponse()
                .setId(product.getId())
                .setShopId(product.getShopId())
                .setName(product.getName())
                .setBrand(product.getBrand())
                .setPrice(product.getPrice())
                .setStockNumber(product.getStockNumber());
    }

    public List<ProductResponse> productListToProductResponseList(List<Product> products) {
        if (products.isEmpty()) {
            return List.of();
        }
        return products.stream()
                .map(this::productToProductResponse)
                .toList();
    }
}

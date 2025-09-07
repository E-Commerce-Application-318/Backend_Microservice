package com.backend.ddd.application.mapper;

import com.backend.ddd.application.model.ProductResponse;
import com.backend.ddd.application.model.ShopResponse;
import com.backend.ddd.domain.model.entity.Shop;
import com.backend.ddd.infrastructure.persistence.client.ExternalProduct;

import java.util.List;

public class ShopMapper {
    public ShopResponse shopToShopResponse(Shop shop) {
        return new ShopResponse()
                .setId(shop.getId())
                .setName(shop.getName())
                .setAddress(shop.getAddress())
                .setDescription(shop.getDescription());
    }

    public List<ProductResponse> externalProductListToProductResponseList(List<ExternalProduct> externalProductsList) {
        return externalProductsList.stream()
                .map(this::externalProductToProductResponse)
                .toList();
    }

    private ProductResponse externalProductToProductResponse(ExternalProduct externalProduct) {
        return new ProductResponse()
                .setId(externalProduct.getId())
                .setShopId(externalProduct.getShopId())
                .setName(externalProduct.getName())
                .setBrand(externalProduct.getBrand())
                .setPrice(externalProduct.getPrice())
                .setStockNumber(externalProduct.getStockNumber());
    }
}

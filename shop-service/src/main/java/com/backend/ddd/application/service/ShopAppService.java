package com.backend.ddd.application.service;

import com.backend.ddd.application.model.ProductResponse;
import com.backend.ddd.application.model.ShopProductsResponse;
import com.backend.ddd.application.model.ShopResponse;

import java.util.List;
import java.util.UUID;

public interface ShopAppService {
    ShopResponse getShopDetailById(UUID shopId);
    List<ProductResponse> getProductsByShopId(UUID shopId);
}

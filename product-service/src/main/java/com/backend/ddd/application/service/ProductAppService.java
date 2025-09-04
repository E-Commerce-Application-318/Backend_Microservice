package com.backend.ddd.application.service;

import com.backend.ddd.application.model.ProductRequest;
import com.backend.ddd.application.model.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductAppService {
    List<ProductResponse> getAllProductsPagaination(Integer page, Integer pageSize, String sortBy, String sortOrder);
    ProductResponse getProductById(UUID id);

    // application for seller
    List<ProductResponse> getProductsByShopIdPagination(UUID shopId, Integer page, Integer pageSize, String sortBy, String sortOrder);
    ProductResponse addProduct(UUID shopId, ProductRequest productRequest);
    ProductResponse updateProduct(ProductRequest productRequest);

}

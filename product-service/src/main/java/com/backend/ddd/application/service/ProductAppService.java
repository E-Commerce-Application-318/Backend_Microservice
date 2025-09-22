package com.backend.ddd.application.service;

import com.backend.ddd.controller.model.dto.ProductRequestDTO;
import com.backend.ddd.controller.model.dto.ProductResponseDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductAppService {
    List<ProductResponseDTO> getAllProductsPagaination(Integer page, Integer pageSize, String sortBy, String sortOrder);
    ProductResponseDTO getProductById(UUID id);
    List<ProductResponseDTO> getProductsByProductIds(List<UUID> productIds);

    // application for seller
    List<ProductResponseDTO> getProductsByShopIdPagination(UUID shopId, Integer page, Integer pageSize, String sortBy, String sortOrder);
    ProductResponseDTO addProduct(UUID shopId, ProductRequestDTO productRequestDTO);
    ProductResponseDTO updateProduct(ProductRequestDTO productRequestDTO);

    Boolean updateProductsProcessOrder(Map<UUID, Integer> productIdAndStockNumberMap);
}

package com.backend.ddd.domain.service;

import com.backend.ddd.domain.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductDomainService {
    Page<Product> getAllProductsPagination(Pageable pageable);
    Optional<Product> getProductById(UUID id);
    List<Product> getProductsByProductIds(List<UUID> productIds);

    Page<Product> getAllProductsByShopIdPagination(UUID shopId, Pageable pageable);
    Product addProduct(Product product);
}

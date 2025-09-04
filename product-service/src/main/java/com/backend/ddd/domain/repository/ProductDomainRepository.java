package com.backend.ddd.domain.repository;

import com.backend.ddd.domain.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ProductDomainRepository {

    Page<Product> getAllProductsPagination(Pageable pageable);
    Optional<Product> getProductById(UUID id);

    Page<Product> getAllProductByShopIdPagination(UUID shopId, Pageable pageable);
    Product addProduct(Product product);

}

package com.backend.ddd.domain.service.impl;

import com.backend.ddd.domain.model.entity.Product;
import com.backend.ddd.domain.repository.ProductDomainRepository;
import com.backend.ddd.domain.service.ProductDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductDomainServiceImpl implements ProductDomainService {

    @Autowired
    private ProductDomainRepository productDomainRepository;

    @Override
    public Page<Product> getAllProductsPagination(Pageable pageable) {
        return productDomainRepository.getAllProductsPagination(pageable);
    }

    @Override
    public Optional<Product> getProductById(UUID id) {
        return productDomainRepository.getProductById(id);
    }

    @Override
    public List<Product> getProductsByProductIds(List<UUID> productIds) {
        return productDomainRepository.getProductsByProductIds(productIds);
    }

    @Override
    public Page<Product> getAllProductsByShopIdPagination(UUID shopId, Pageable pageable) {
        return productDomainRepository.getAllProductByShopIdPagination(shopId, pageable);
    }

    @Override
    public Product addProduct(Product product) {
        return productDomainRepository.addProduct(product);
    }

    @Override
    public List<Product> updateProducts (List<Product> products) {
        return productDomainRepository.updateProducts(products);
    }
}

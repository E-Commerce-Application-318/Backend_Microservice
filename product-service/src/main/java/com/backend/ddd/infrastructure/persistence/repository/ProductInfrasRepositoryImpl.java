package com.backend.ddd.infrastructure.persistence.repository;

import com.backend.ddd.domain.model.entity.Product;
import com.backend.ddd.domain.repository.ProductDomainRepository;
import com.backend.ddd.infrastructure.persistence.mapper.ProductJPAMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductInfrasRepositoryImpl implements ProductDomainRepository {

    @Autowired
    private ProductJPAMapper productJPAMapper;

    @Override
    public Page<Product> getAllProductsPagination(Pageable pageable) {
        return productJPAMapper.findAll(pageable);
    }

    @Override
    public Optional<Product> getProductById(UUID id) {
        return productJPAMapper.findById(id);
    }

    @Override
    public List<Product> getProductsByProductIds(List<UUID> productIds) {
        return productJPAMapper.findByProductIds(productIds);
    }

    @Override
    public Page<Product> getAllProductByShopIdPagination(UUID shopId, Pageable pageable) {
        return productJPAMapper.findAllByShopId(shopId, pageable);
    }

    @Override
    public Product addProduct(Product product) {
        return productJPAMapper.save(product);
    }
}

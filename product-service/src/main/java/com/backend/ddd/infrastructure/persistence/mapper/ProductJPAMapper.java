package com.backend.ddd.infrastructure.persistence.mapper;

import com.backend.ddd.domain.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ProductJPAMapper extends JpaRepository<Product, UUID> {

    @Query("select p from Product p where p.shopId = :shopId")
    Page<Product> findAllByShopId(@Param("shopId") UUID shopId, Pageable pageable);

}

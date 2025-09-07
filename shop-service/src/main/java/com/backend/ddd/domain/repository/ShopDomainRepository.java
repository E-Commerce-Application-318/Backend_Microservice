package com.backend.ddd.domain.repository;

import com.backend.ddd.domain.model.entity.Shop;

import java.util.Optional;
import java.util.UUID;

public interface ShopDomainRepository {
    Optional<Shop> findById(UUID id);
}

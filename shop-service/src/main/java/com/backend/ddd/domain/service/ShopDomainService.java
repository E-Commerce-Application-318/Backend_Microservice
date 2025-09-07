package com.backend.ddd.domain.service;

import com.backend.ddd.domain.model.entity.Shop;

import java.util.Optional;
import java.util.UUID;

public interface ShopDomainService {
    Optional<Shop> findById(UUID id);
}

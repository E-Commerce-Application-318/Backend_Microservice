package com.backend.ddd.domain.service.impl;

import com.backend.ddd.domain.model.entity.Shop;
import com.backend.ddd.domain.repository.ShopDomainRepository;
import com.backend.ddd.domain.service.ShopDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ShopDomainServiceImpl implements ShopDomainService {
    @Autowired
    private ShopDomainRepository shopDomainRepository;

    public Optional<Shop> findById(UUID shopId) {
        return shopDomainRepository.findById(shopId);
    }
}

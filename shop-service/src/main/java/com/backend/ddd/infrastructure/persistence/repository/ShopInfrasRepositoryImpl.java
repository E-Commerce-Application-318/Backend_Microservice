package com.backend.ddd.infrastructure.persistence.repository;

import com.backend.ddd.domain.model.entity.Shop;
import com.backend.ddd.domain.repository.ShopDomainRepository;
import com.backend.ddd.infrastructure.persistence.mapper.ShopJPAMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ShopInfrasRepositoryImpl implements ShopDomainRepository {
    @Autowired
    private ShopJPAMapper shopJPAMapper;

    @Override
    public Optional<Shop> findById(UUID shopId) {
        return shopJPAMapper.findById(shopId);
    }

}

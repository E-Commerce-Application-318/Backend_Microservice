package com.backend.ddd.infrastructure.persistence.mapper;

import com.backend.ddd.domain.model.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShopJPAMapper extends JpaRepository<Shop, UUID> {
}

package com.backend.ddd.application.service.impl;

import com.backend.ddd.application.mapper.ShopMapper;
import com.backend.ddd.application.model.ProductResponse;
import com.backend.ddd.application.model.ShopResponse;
import com.backend.ddd.application.service.ShopAppService;
import com.backend.ddd.domain.model.entity.Shop;
import com.backend.ddd.domain.service.ShopDomainService;
import com.backend.ddd.infrastructure.persistence.client.ExternalProduct;
import com.backend.ddd.infrastructure.persistence.client.ProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShopAppServiceImpl implements ShopAppService {

    @Autowired
    private ShopDomainService shopDomainService;

    private final ShopMapper shopMapper = new ShopMapper();

    @Autowired
    private ProductClient productClient;

    @Override
    public ShopResponse getShopDetailById(UUID shopId) {
        Optional<Shop> shop = shopDomainService.findById(shopId);
        return shop.map(shopMapper::shopToShopResponse).orElse(null);
    }

    @Override
    public List<ProductResponse> getProductsByShopId(UUID shopId) {
        List<ExternalProduct> externalProductList = productClient.getAllProductsByShopId(shopId);
        if  (externalProductList == null)
            return null;
        return shopMapper.externalProductListToProductResponseList(externalProductList);
    }

}

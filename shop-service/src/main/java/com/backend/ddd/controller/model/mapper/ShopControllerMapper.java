package com.backend.ddd.controller.model.mapper;

import com.backend.ddd.application.model.ShopResponse;
import com.backend.ddd.controller.model.dto.ShopResponseDTO;

public class ShopControllerMapper {
    public ShopResponseDTO shopResponseToShopResponseDTO(ShopResponse shopResponse) {
        return new ShopResponseDTO()
                .setId(shopResponse.getId())
                .setName(shopResponse.getName())
                .setAddress(shopResponse.getAddress())
                .setDescription(shopResponse.getDescription());
    }
}

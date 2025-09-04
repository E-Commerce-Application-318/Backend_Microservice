package com.backend.ddd.controller.model.mapper;

import com.backend.ddd.application.model.ProductRequest;
import com.backend.ddd.application.model.ProductResponse;
import com.backend.ddd.controller.model.dto.ProductRequestDTO;
import com.backend.ddd.controller.model.dto.ProductResponseDTO;

import java.util.List;
import java.util.UUID;

public class ProductControllerMapper {
    public ProductResponseDTO productResponseToProductResponseDTO(ProductResponse productResponse) {
        return new ProductResponseDTO()
                .setId(productResponse.getId())
                .setShopId(productResponse.getShopId())
                .setName(productResponse.getName())
                .setDescription(productResponse.getDescription())
                .setBrand(productResponse.getBrand())
                .setPrice(productResponse.getPrice())
                .setStockNumber(productResponse.getStockNumber());
    }

    public List<ProductResponseDTO> productResponseListToProductReponseDTOList(List<ProductResponse> productResponseList) {
        if (productResponseList.isEmpty()) {
            return List.of();
        }
        return productResponseList.stream()
                .map(this::productResponseToProductResponseDTO)
                .toList();
    }

    public ProductRequest productRequestDTOToProductRequest(ProductRequestDTO productRequestDTO) {
        return new ProductRequest()
                .setName(productRequestDTO.getName())
                .setDescription(productRequestDTO.getDescription())
                .setBrand(productRequestDTO.getBrand())
                .setPrice(productRequestDTO.getPrice())
                .setStockNumber(productRequestDTO.getStockNumber());
    }
}

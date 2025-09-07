package com.backend.ddd.controller.model.dto;

import com.backend.ddd.application.model.ProductResponse;
import com.backend.ddd.application.model.ShopResponse;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain=true)
public class ShopProductsResponseDTO {
    private ShopResponse shopResponse;
    private List<ProductResponse> productResponses;
}

package com.backend.ddd.application.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class ShopProductsResponse {
    ShopResponse shopResponse;

}

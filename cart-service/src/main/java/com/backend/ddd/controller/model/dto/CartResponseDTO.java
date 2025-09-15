package com.backend.ddd.controller.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class CartResponseDTO {
    private UUID id;
    private Integer totalItem;
    private Double totalAmount;
    List<ProductResponseDTO> productReponseDTOList;

}

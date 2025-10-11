package com.backend.ddd.controller.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CartBasketResponseDTO {
    private Integer totalItem;
    private Double totalAmount;
    List<CartResponseDTO> cartResponseDTOs;
}

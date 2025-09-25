package com.backend.ddd.controller.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Data
@Accessors(chain = true)
public class OrderResponseDTO {
    private UUID id;
    private Double totalAmount;
    private String status;
    private String shippingAddress;
    private String phoneNumber;
    private Date createdAt;
    List<OrderItemDTO> orderItemDTOs;
}

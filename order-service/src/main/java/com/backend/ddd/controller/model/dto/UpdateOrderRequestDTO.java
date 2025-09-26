package com.backend.ddd.controller.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateOrderRequestDTO {
    private UUID orderId;
    private String address;
    private String phoneNumber;
}

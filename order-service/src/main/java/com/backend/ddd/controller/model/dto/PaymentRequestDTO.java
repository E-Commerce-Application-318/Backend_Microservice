package com.backend.ddd.controller.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PaymentRequestDTO {
    private UUID orderId;
    private String serialNumber;
    private String expiredDate;
    private String cvv;
}

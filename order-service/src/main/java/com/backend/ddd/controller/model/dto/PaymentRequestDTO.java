package com.backend.ddd.controller.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain=true)
public class PaymentRequestDTO {
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;
}

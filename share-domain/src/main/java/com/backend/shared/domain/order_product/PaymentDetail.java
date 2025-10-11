package com.backend.shared.domain.order_product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetail {
    private UUID userId;
    private UUID orderId;
    private Double totalAmount;
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;
}

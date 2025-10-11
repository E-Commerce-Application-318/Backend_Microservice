package com.backend.ddd.domain.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Entity
@Accessors(chain=true)
@Table(name="payments")
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @Column(name="id", columnDefinition = "BINARY(16)")
    @GeneratedValue
    private UUID id;

    @Column(name="user_id", columnDefinition="BINARY(16)")
    private UUID userId;

    @Column(name="card_number")
    private String cardNumber;

    @Column(name="card_holder_name")
    private String cardHolderName;

    @Column(name="expiry_date")
    private String expiryDate;

    @Column(name="cvv")
    private String cvv;

    @Column(name="balance")
    private Double balance;
}

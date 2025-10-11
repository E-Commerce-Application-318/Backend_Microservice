package com.backend.shared.domain.order_product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCancelledEventRestock {
    private Map<UUID, Integer> productIdsAndQuantities;
}

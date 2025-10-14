package com.backend.ddd.application.mapper;

import com.backend.ddd.controller.model.dto.OrderItemDTO;
import com.backend.ddd.controller.model.dto.OrderResponseDTO;
import com.backend.ddd.controller.model.dto.PaymentRequestDTO;
import com.backend.ddd.domain.model.entity.Order;
import com.backend.ddd.domain.model.entity.OrderItem;
import com.backend.shared.domain.order_product.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class OrderAppMapper {
    private OrderItemDTO orderItemToOrderItemDTO(OrderItem orderItem) {
        return new OrderItemDTO()
                .setProductId(orderItem.getOrderItemId().getProductId())
                .setProductName(orderItem.getProductName())
                .setQuantity(orderItem.getQuantity())
                .setUnitPrice(orderItem.getUnitPrice())
                .setTotalPrice(orderItem.getTotalPrice());
    }
    private List<OrderItemDTO> orderItemsToOrderItemDTOs(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(this::orderItemToOrderItemDTO)
                .toList();
    }
    public OrderResponseDTO OrderAndOrderItemsToOrderResponseDTO(Order order, List<OrderItem> orderItems) {
        return new OrderResponseDTO()
                .setId(order.getId())
                .setTotalAmount(order.getTotalAmount())
                .setStatus(order.getStatus())
                .setUserFullName(order.getUserFullName())
                .setShippingAddress(order.getShippingAddress())
                .setPhoneNumber(order.getPhoneNumber())
                .setCreatedAt(order.getCreatedAt())
                .setOrderItemDTOs(this.orderItemsToOrderItemDTOs(orderItems));
    }

    public OrderCreatedEvent createOrderCreatedEvent(Order order, List<OrderItem> orderItems, List<UUID> cartIds) {
        List<OrderItemEvent> orderItemEvents = orderItems.stream()
                .map(item -> new OrderItemEvent(
                        item.getOrderItemId().getProductId(),
                        item.getProductName(),
                        item.getBrand(),
                        item.getQuantity(),
                        item.getUnitPrice()
                )).toList();
        return new OrderCreatedEvent(order.getUserId(), order.getTotalAmount(), orderItemEvents, cartIds);
    }

    public OrderCancelledEventRestock createOrderCancelledEventRestock(List<OrderItem> orderItems) {
        Map<UUID, Integer> productIdsAndQuantities = new HashMap<>();
        // Use negative quantities to restock in product-service
        orderItems.forEach(item -> productIdsAndQuantities.put(item.getOrderItemId().getProductId(), -item.getQuantity()));
        return new OrderCancelledEventRestock(productIdsAndQuantities);
    }
    public OrderCancelledEventRefund createOrderCancelledEventRefund(UUID userId, Double totalAmount) {
           return new OrderCancelledEventRefund(userId, totalAmount);
    }
    public PaymentDetail paymentRequestDtoToPaymentDetail(UUID userId, UUID orderId, Double totalAmount, PaymentRequestDTO paymentRequestDTO) {
        return new PaymentDetail(
                userId,
                orderId,
                totalAmount,
                paymentRequestDTO.getCardNumber(),
                paymentRequestDTO.getCardHolderName(),
                paymentRequestDTO.getExpiryDate(),
                paymentRequestDTO.getCvv()
        );
    }
}

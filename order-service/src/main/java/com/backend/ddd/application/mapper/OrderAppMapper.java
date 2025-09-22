package com.backend.ddd.application.mapper;

import com.backend.ddd.controller.model.dto.OrderItemDTO;
import com.backend.ddd.controller.model.dto.OrderResponseDTO;
import com.backend.ddd.domain.model.entity.Order;
import com.backend.ddd.domain.model.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderAppMapper {
    private OrderItemDTO orderItemToOrderItemDTO(OrderItem orderItem) {
        return new OrderItemDTO()
                .setProductId(orderItem.getOrderItemId().getProductId())
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
                .setShippingAddress(order.getShippingAddress())
                .setPhoneNumber(order.getPhoneNumber())
                .setCreatedAt(order.getCreatedAt())
                .setOrderItemDTOs(this.orderItemsToOrderItemDTOs(orderItems));
    }
}

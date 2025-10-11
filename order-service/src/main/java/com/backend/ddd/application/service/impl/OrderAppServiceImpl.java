package com.backend.ddd.application.service.impl;

import com.backend.ddd.application.mapper.OrderAppMapper;
import com.backend.ddd.application.service.OrderAppService;
import com.backend.ddd.controller.model.dto.OrderResponseDTO;
import com.backend.ddd.controller.model.dto.PaymentRequestDTO;
import com.backend.ddd.domain.model.entity.Order;
import com.backend.ddd.domain.model.entity.OrderItem;
import com.backend.ddd.domain.model.entity.OrderItemId;
import com.backend.ddd.domain.service.OrderDomainService;
import com.backend.ddd.infrastructure.persistence.client.AuthClient;
import com.backend.ddd.infrastructure.persistence.client.CartClient;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalCartResponse;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalUserResponse;

import com.backend.shared.domain.order_product.OrderCreatedEvent;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
//@RequiredArgsConstructor
public class OrderAppServiceImpl implements OrderAppService {

    @Autowired
    private CartClient cartClient;

    @Autowired
    private AuthClient authClient;

    @Autowired
    private OrderDomainService orderDomainService;

    @Autowired
    private OrderAppMapper orderAppMapper;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public List<OrderResponseDTO> getAllOrdersByUserId(UUID userId) {
        List<Order> orders = orderDomainService.getOrdersByUserId(userId);
        if (orders.isEmpty()) {
            return null;
        }
        return orders.stream()
                .map(order -> {
                    List<OrderItem> orderItems = orderDomainService.getOrderItemsByOrderId(order.getId());
                    return orderAppMapper.OrderAndOrderItemsToOrderResponseDTO(order, orderItems);
                })
                .toList();
    }

    @Override
    @Transactional
    public OrderResponseDTO createOrder(UUID userId, List<UUID> cartIds) {
        // get all productIds from cartIds
        List<ExternalCartResponse> externalCarts = cartClient.getCartsByCartIds(cartIds);

        // processing the order -> create the order with the all information including, all products
        // status: pending -> need payment

        UUID orderId = UUID.randomUUID(); // generate orderId first to create OrderItems with orderId

        // convert all carts to orderItems (create a list
        List<OrderItem> orderItems = externalCarts.stream()
            .map(cart -> new OrderItem(
                    new OrderItemId(orderId, cart.getProductId()),
                    cart.getProductName(),
                    cart.getBrand(),
                    cart.getQuantity(),
                    cart.getPrice(),
                    cart.getPrice() * cart.getQuantity()))
            .collect(Collectors.toList());

        // calculate the total amount of all item
        Double totalAmount = orderItems.stream()
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();


        ExternalUserResponse user = authClient.getAddressByUserId(userId);
        log.info("CHECK USER: {}", user);
        Order order = new Order()
                .setId(orderId)
                .setUserId(userId)
                .setTotalAmount(totalAmount)
                .setStatus("Payment Required")
                .setUserFullName(user.getName())
                .setShippingAddress(user.getAddress())
                .setPhoneNumber(user.getPhoneNumber())
                .setCreatedAt(new Date());

        // save order and orderItems into database
        Order savedOrder = orderDomainService.saveOrder(order);
        List<OrderItem> savedOrderItems = orderDomainService.saveOrderItems(orderItems);


        if (savedOrder == null || savedOrderItems.size() != orderItems.size() || savedOrderItems.isEmpty()) {
            throw new RuntimeException("Failed to create the order.");
        }

        // publish the event when the order and orderItems were saved -> use application approach to publish the event
        // consumer:
        // - product to reduce the stock number
        // - cart to remove the cart by cartIds
        // - payment will process to reduce the amount of money in cart of customer
        OrderCreatedEvent orderCreatedEvent = orderAppMapper.createOrderCreatedEvent(savedOrder, savedOrderItems, cartIds);
        applicationEventPublisher.publishEvent(orderCreatedEvent);

        // processing the order -> after confirmation from user -> input correct payment -> change the status into Preparing and Shipping
        return orderAppMapper.OrderAndOrderItemsToOrderResponseDTO(savedOrder, savedOrderItems);
    }

    @Override
    public String updateOrderAddressPhoneNumber(UUID orderId, String address, String phoneNumber) {
        Order order = orderDomainService.getOrderById(orderId);
        if (order == null) {
            return "Not Found the order by orderId: " + orderId;
        }
        if (address != null) {
            order.setShippingAddress(address);
        }
        if (phoneNumber != null) {
            order.setPhoneNumber(phoneNumber);
        }
        try {
            orderDomainService.saveOrder(order);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
        return "Updated successfully";
    }

    @Override
    @Transactional
    public String processPayment (UUID orderId, UUID userId, PaymentRequestDTO paymentRequestDTO) {
        try {
            Order order = orderDomainService.getOrderById(orderId);
            log.info("Order: {}", order);
            // just verify
            if (order == null || !order.getUserId().equals(userId)) {
                return "Payment process failed because order with order ID not exist or not belongs to this user Id";
            }
            applicationEventPublisher.publishEvent(orderAppMapper.paymentRequestDtoToPaymentDetail(userId, orderId, order.getTotalAmount(), paymentRequestDTO));
        } catch (Exception err) {
            return "Payment process failed because: " + err.getMessage();
        }
        return "Payment process successfully";
    }

    @Override
    @Transactional
    public Boolean cancelOrder(UUID orderId) {
        try {
            Order order = orderDomainService.getOrderById(orderId);
            List<OrderItem> orderItems = orderDomainService.getOrderItemsByOrderId(orderId);
            if (order.getStatus().equals("PAID")) {
                log.info("Refund for this order {}", orderId);
                applicationEventPublisher.publishEvent(orderAppMapper.createOrderCancelledEventRefund(order.getUserId(), order.getTotalAmount()));
            }
            applicationEventPublisher.publishEvent(orderAppMapper.createOrderCancelledEventRestock(orderItems));
            orderDomainService.cancelOrder(orderId);
        } catch (Exception e) {
            throw (new RuntimeException("Failed to delete the order. Error: " + e.getMessage()));
        }
        return true;
    }

    @Override
    @Transactional
    public void updateOrderStatus(UUID orderId, String status) {
        orderDomainService.updateOrderStatus(orderId, status);
    }
}

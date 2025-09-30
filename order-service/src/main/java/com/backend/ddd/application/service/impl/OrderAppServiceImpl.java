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
import com.backend.ddd.infrastructure.persistence.client.ProductClient;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalProduct;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalUser;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Slf4j
@Service
public class OrderAppServiceImpl implements OrderAppService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private CartClient cartClient;

    @Autowired
    private AuthClient authClient;

    @Autowired
    private OrderDomainService orderDomainService;

    @Autowired
    private OrderAppMapper orderAppMapper;

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
    public OrderResponseDTO createOrder(UUID userId, List<UUID> cartIds) {
        // get all productIds from cartIds
        Map<UUID, Integer> productIdsAndQuantities = cartClient.getProductIdsAndQuantitiesByCartIds(cartIds);

        // get all product details from product-service by productIds
        List<UUID> productIds = productIdsAndQuantities.keySet().stream().toList();
        List<ExternalProduct> externalProducts = productClient.getProductsByProductIds(productIds);

        // check the quantity of the product in cart with the stock number of product in database
        // assume it will have enough stock for al products in the cart

        // processing the order -> reduce the stock number of product
        Boolean result = productClient.processOrder(productIdsAndQuantities);
        if (!result) {
            throw new RuntimeException("Failed to process the order due to insufficient stock.");
        }

        // processing the order -> create the order with the all information including, all products
        // status: pending -> need payment

        UUID orderId = UUID.randomUUID(); // generate orderId first to create OrderItems with orderId
        Double totalAmount = 0.0; // calculate the total amount of the order
        List<OrderItem> orderItems = new ArrayList<>();

        for (ExternalProduct product : externalProducts) {
            Double totalProductPrice = product.getPrice() * productIdsAndQuantities.get(product.getId());
            totalAmount += totalProductPrice;
            OrderItem orderItem = new OrderItem()
                    .setOrderItemId(new OrderItemId(orderId, product.getId()))
                    .setQuantity(productIdsAndQuantities.get(product.getId()))
                    .setUnitPrice(product.getPrice())
                    .setTotalPrice(totalProductPrice);
            orderItems.add(orderItem);
        }

        ExternalUser user = authClient.getAddressByUserId(userId);
        Order order = new Order()
                .setId(orderId)
                .setUserId(userId)
                .setTotalAmount(totalAmount)
                .setStatus("Payment Required")
                .setShippingAddress(user.getAddress())
                .setPhoneNumber(user.getPhoneNumber())
                .setCreatedAt(new Date());

        // save order and orderItems into database
        Order savedOrder = orderDomainService.saveOrder(order);
        List<OrderItem> savedOrderItems = orderDomainService.saveOrderItems(orderItems);

        if (savedOrder == null || savedOrderItems.size() != orderItems.size() || savedOrderItems.isEmpty()) {
            throw new RuntimeException("Failed to create the order.");
        }

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
        Order order = orderDomainService.getOrderById(orderId);
        log.info("Order: {}", order);
        if (order == null || !order.getUserId().equals(userId)) {
            return "Payment process failed because order with order ID not exist or not belongs to this user Id";
        }
        Boolean paymentConfirmation = authClient.paymentOrder(userId, paymentRequestDTO);
        log.info("Check: {}", paymentConfirmation);
        if (!paymentConfirmation) {
            return "Payment process failed!";
        }
        try {
            log.info("Payment confirmation: {}", orderId);
            orderDomainService.processPayment(orderId);
            return "Payment process successfully";
        } catch (Exception err) {
            return "Payment process failed! Error: " + err;
        }
    }
}

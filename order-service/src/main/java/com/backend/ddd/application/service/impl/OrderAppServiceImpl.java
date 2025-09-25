package com.backend.ddd.application.service.impl;

import com.backend.ddd.application.mapper.OrderAppMapper;
import com.backend.ddd.application.service.OrderAppService;
import com.backend.ddd.controller.model.dto.OrderResponseDTO;
import com.backend.ddd.domain.model.entity.Order;
import com.backend.ddd.domain.model.entity.OrderItem;
import com.backend.ddd.domain.model.entity.OrderItemId;
import com.backend.ddd.domain.service.OrderDomainService;
import com.backend.ddd.infrastructure.persistence.client.AuthClient;
import com.backend.ddd.infrastructure.persistence.client.CartClient;
import com.backend.ddd.infrastructure.persistence.client.ProductClient;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalProduct;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Service
public class OrderAppServiceImpl implements OrderAppService {

    @Autowired
    private ProductClient productWebClient;

    @Autowired
    private CartClient cartWebClient;

    @Autowired
    private AuthClient authWebClient;

    @Autowired
    private OrderDomainService orderDomainService;

    @Autowired
    private OrderAppMapper orderMapper;


    @Override
    public OrderResponseDTO createOrder(UUID userId, List<UUID> cartIds) {
        // get all productIds from cartIds
        Map<UUID, Integer> productIdsAndQuantities = cartWebClient.getProductIdsAndQuantitiesByCartIds(cartIds);

        // get all product details from product-service by productIds
        List<UUID> productIds = productIdsAndQuantities.keySet().stream().toList();
        List<ExternalProduct> externalProducts = productWebClient.getProductsByProductIds(productIds);

        // check the quantity of the product in cart with the stock number of product in database
        // assume it will have enough stock for al products in the cart

        // processing the order -> reduce the stock number of product
        Boolean result = productWebClient.processOrder(productIdsAndQuantities);
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

        ExternalUser user = authWebClient.getAddressByUserId(userId);
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

        return orderMapper.OrderAndOrderItemsToOrderResponseDTO(savedOrder, savedOrderItems);

    }
    /** Delete an order:
     *  1. Find the order by id
     *  2. Get all items in the order
     *  3. Call product-service to refund product quantities
     *  4. Delete order items
     *  5. Delete the order itself
     */
    @Override
    @Transactional
    public boolean deleteOrder(UUID orderId) {
        // Step 1: Check if order exists
        Order order = orderDomainService.findOrderById(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found: " + orderId);
        }

        // Step 2: Get items of the order
        List<OrderItem> items = orderDomainService.findOrderItemsByOrderId(orderId);

        // Step 3: Build productId -> quantity map for refund
        Map<UUID, Integer> productQuantities = items.stream()
                .collect(Collectors.toMap(
                        oi -> oi.getOrderItemId().getProductId(),
                        OrderItem::getQuantity,
                        Integer::sum
                ));

        // Step 4: Call product-service to refund stock
        if (!productQuantities.isEmpty()) {
            Boolean ok = productWebClient.refundOrder(productQuantities);
            if (!Boolean.TRUE.equals(ok)) {
                throw new RuntimeException("Refund inventory failed");
            }
        }

        // Step 5: Delete items then delete order
        orderDomainService.deleteOrderItemsByOrderId(orderId);
        orderDomainService.deleteOrderById(orderId);

        return true;
    }

}

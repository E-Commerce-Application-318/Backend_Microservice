package com.backend.ddd.application.service.impl;

import com.backend.shared.domain.order_product.OrderCancelledEvent;
import com.backend.shared.domain.order_product.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderPublisher {
    private final StreamBridge streamBridge;

    public OrderPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    /*
        Consumer:
        - product-service: update stock number of product, auth-service
     */

    /**
     *
     * @param event
     *
     * Consumer:
     * - product-service: update stock number of product (reduce the corresponding quantity)
     * - auth-service (use as payment): update the amount money in user account
     * - cart-service: clear the cart after checkout
     */
    @EventListener
    public void handleCartCheckOutEvent(OrderCreatedEvent event) {
        try {
            if (streamBridge.send("orderCreatedChannel", event))
                log.info("Published event for CREATING order successfully");
            else
                throw(new Exception("Failed to send OrderCreatedEvent for order"));

        } catch (Exception e) {
            log.error("Error while sending OrderCreatedEvent for order: ", e);
        }
    }

    /**
     *
     * @param event
     *
     * Consumer:
     * - product-service: update stock number of product (increase the corresponding quantity)
     * - not implment yet auth-service (use as payment): refund the amount money in user account
     */
    @EventListener
    public void handleCancelOrderEvent(OrderCancelledEvent event) {
        try {
            if (streamBridge.send("orderCancelledChannel", event))
                throw(new Exception("Failed to send OrderCancelledEvent for order"));
            else
                log.info("Published event for CANCELLING order successfully");
        } catch (Exception e) {
            log.error("Error while sending OrderCancelledEvent for order: ", e);
        }
    }
}

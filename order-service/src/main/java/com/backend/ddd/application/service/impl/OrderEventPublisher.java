package com.backend.ddd.application.service.impl;

import com.backend.shared.domain.order_product.OrderCancelledEventRefund;
import com.backend.shared.domain.order_product.OrderCancelledEventRestock;
import com.backend.shared.domain.order_product.OrderCreatedEvent;
import com.backend.shared.domain.order_product.PaymentDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderEventPublisher {
    private final StreamBridge streamBridge;

    public OrderEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }
    
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
     * @param orderCancelledEventRestock
     *
     * Consumer:
     * - product-service: update stock number of product (increase the corresponding quantity)
     * - not implment yet auth-service (use as payment): refund the amount money in user account
     */
    @EventListener
    public void handleCancelOrderEvent(OrderCancelledEventRestock orderCancelledEventRestock) {
        try {
            if (streamBridge.send("orderCancelledChannelRestock", orderCancelledEventRestock))
                log.info("Published event for CANCELLING order to restock successfully");
            else
                log.info("Failed to send OrderCancelledEvent for order");
        } catch (Exception e) {
            log.error("Error while sending OrderCancelledEvent for order to restock: ", e);
        }
    }

    /**
     *
     * @param orderCancelledEventRefund
     */
    @EventListener
    public void handleCancelOrderEvent(OrderCancelledEventRefund orderCancelledEventRefund) {
        try {
            if (streamBridge.send("orderCancelledEventRefund", orderCancelledEventRefund))
                log.info("Published event for CANCELLING order to refund successfully");
            else
                log.info("Failed to send OrderCancelledEvent for order");
        } catch (Exception e) {
            log.error("Error while sending OrderCancelledEvent for order to refund: ", e);
        }
    }

    /**
     * 
     * @param paymentDetail
     */
    @EventListener
    public void handlePaymentProcessEvent(PaymentDetail paymentDetail) {
        try {
            if (streamBridge.send("paymentProcessChannel", paymentDetail))
                log.info("Published event for PAYMENT PROCESSING successfully");
            else
                throw(new Exception("Failed to send PaymentDetail for order"));
        } catch (Exception e) {
            log.error("Error while sending PaymentDetail for order: ", e);
        }
    }
}

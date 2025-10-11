package com.backend.ddd.application.service.impl;

import com.backend.shared.domain.order_product.PaymentStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthEventPublisher {
    private final StreamBridge streamBridge;

    public AuthEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    /**
     * @param paymentStatus
     */
    @EventListener
    public void handleStatusUpdatedEvent(PaymentStatus paymentStatus) {
        try {
            if (streamBridge.send("statusUpdatedChannel", paymentStatus))
                log.info("Published event for UPDATING status successfully");
            else
                throw(new Exception("Failed to send StatusUpdatedEvent for auth"));
        } catch (Exception e) {
            log.info("Error while sending StatusUpdatedEvent for auth: " + e);
        }
    }

}

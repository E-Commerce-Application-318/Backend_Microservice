package com.backend.ddd.infrastructure.consumer;

import com.backend.ddd.application.service.OrderAppService;
import com.backend.shared.domain.order_product.PaymentStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class OrderConsumerPaymentEvent {
    @Autowired
    private OrderAppService orderAppService;

    @Bean
    public Consumer<PaymentStatus> receivedPaymentStatusEvent() {
        return event -> {
            try {
                log.info("Consumed Order Payment Event");
                if (event.getStatus().equals("SUCCESS")) {
                    orderAppService.updateOrderStatus(event.getOrderId(), "PAID");
                    log.info("Order Payment Event Success");
                } else {
                    orderAppService.updateOrderStatus(event.getOrderId(), "PAYMENT_FAILED. Required payment again");
                    log.info("Order Payment Failed");
                }
            } catch (Exception e) {
                log.error("Error while processing PaymentStatus event: ", e);
            }
        };
    }
}

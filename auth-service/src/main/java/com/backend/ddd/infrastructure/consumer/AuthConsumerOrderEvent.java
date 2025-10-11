package com.backend.ddd.infrastructure.consumer;

import com.backend.ddd.application.service.AuthAppService;
import com.backend.shared.domain.order_product.OrderCancelledEventRefund;
import com.backend.shared.domain.order_product.PaymentDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class AuthConsumerOrderEvent {
    @Autowired
    private AuthAppService authAppService;

    @Bean
    public Consumer<PaymentDetail> receivedPaymentProcessingEvent() {
        return event -> {
            try {
                String result = authAppService.processPayment(event);
                if (result.equals("Payment successful"))
                    log.info("Payment processed successfully");
                else
                    log.info("Payment processing failed: " + result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Bean
    public Consumer<OrderCancelledEventRefund> receivedOrderCancelledEventRefund() {
        return event -> {
            try {
                String result = authAppService.refundPayment(event.getUserId(), event.getTotalAmount());
                if (result.equals("Refund successful"))
                    log.info("Refund processed successfully");
                else
                    log.info("Refund processing failed: " + result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

}

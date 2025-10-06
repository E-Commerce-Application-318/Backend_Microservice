package com.backend.ddd.infrastructure.persistence.consumer;

import com.backend.ddd.application.service.CartAppService;
import com.backend.shared.domain.order_product.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class OrderHandledEvent {

    @Autowired
    private CartAppService cartAppService;

    @Bean
    public Consumer<OrderCreatedEvent> receivedOrderCreatingEvent() {
        return event -> {
            if (cartAppService.removeCartsByCartIds(event.getCartIds()))
                log.info("Order has been removed");
            else
                log.info("Order has been removed");
        };
    }
}

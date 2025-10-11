package com.backend.ddd.infrastructure.consumer;

import com.backend.ddd.application.service.ProductAppService;
import com.backend.shared.domain.order_product.OrderCancelledEventRestock;
import com.backend.shared.domain.order_product.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@Slf4j
@Configuration
public class ProductConsumerOrderEvent {

    @Autowired
    private ProductAppService productAppService;

    @Bean
    public Consumer<OrderCreatedEvent> receivedOrderCreatedEvent () {
        return event -> {
            Map<UUID, Integer> productIdsAndQuantities = new HashMap<>();
            event.getOrderItemEvents().forEach(orderItemEvent -> {
                productIdsAndQuantities.put(orderItemEvent.getProductId(), orderItemEvent.getQuantity());
            });
            try {
                if (productAppService.updateProductsProcessOrder(productIdsAndQuantities))
                    log.info("Product Updated Successfully");
                else {
                    log.info("Product Updated Failed");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Bean
    public Consumer<OrderCancelledEventRestock> receivedOrderCancelledEventRestock () {
        return event -> {
            try {
                if (productAppService.updateProductsProcessOrder(event.getProductIdsAndQuantities()))
                    log.info("Product Updated Successfully");
                else {
                    log.info("Product Updated Failed");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}


















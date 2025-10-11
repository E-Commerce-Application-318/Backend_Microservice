package com.backend.ddd.infrastructure.persistence.consumer;

import com.backend.shared.domain.order_product.OrderCreatedEvent;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.WindowStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.UUID;
import java.util.function.Consumer;

@Configuration
public class StreamProcessor {

    @Value("${windowstore.name}")
    private String WINDOWSTORE_NAME;

    @Bean
    public Consumer<KStream<String, OrderCreatedEvent>> receivedOrderCreatedEvent() {
        return inputStream -> {

            // Add debugging to see if events are received
            inputStream.peek((key, value) -> {
                System.out.println("Stream received event: " + value);
            });

            // transform input stream: extract the productId and quantity
            KTable<Windowed<String>, Integer> quantitiesByProduct = inputStream
                    .flatMap((key, orderCreatedEvent) -> {
                        return orderCreatedEvent.getOrderItemEvents()
                                .stream()
                                .map(orderItem -> {
                                    String productId = orderItem.getProductId().toString();
                                    Integer quantity = orderItem.getQuantity();
                                    System.out.println("Extracted: " + productId + " -> " + quantity);
                                    return KeyValue.pair(productId, quantity);
                                })
                                .toList();
                    })
                    .groupByKey(Grouped.with(Serdes.String(), Serdes.Integer()))
                    .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(30)))
                    .reduce(Integer::sum, Materialized.<String, Integer, WindowStore<Bytes, byte[]>>as(WINDOWSTORE_NAME).withKeySerde(Serdes.String()).withValueSerde(Serdes.Integer()));

            // Write debugging print here
            quantitiesByProduct.toStream()
                    .print(Printed.<Windowed<String>, Integer>toSysOut().withLabel("Quantities by product"));
        };
    }
//    @Bean Consumer<OrderCreatedEvent> receivedOrderCreatedEvent() {
//        return event -> {
//            System.out.println("Received Order Created Event");
//        };
//    }
}

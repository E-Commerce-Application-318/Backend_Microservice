package com.backend.ddd.infrastructure.persistence.consumer;

import com.backend.shared.domain.order_product.OrderCreatedEvent;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.common.utils.Time;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.WindowStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.function.Consumer;

@Configuration
public class StreamProcessor {

    @Value("${windowstore.product_name}")
    private String WINDOWSTORE_NAME_PRODUCT;

    @Value("${windowstore.brand_name}")
    private String WINDOWSTORE_NAME_BRAND;

    @Bean
    public Consumer<KStream<String, OrderCreatedEvent>> receivedOrderCreatedEventForProduct() {
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
                                    String compositeKey = orderItem.getProductId().toString() + " | " + orderItem.getProductName();
                                    Integer quantity = orderItem.getQuantity();
                                    System.out.println("Extracted: " + compositeKey + " -> " + quantity);
                                    return KeyValue.pair(compositeKey, quantity);
                                })
                                .toList();
                    })
                    .groupByKey(Grouped.with(Serdes.String(), Serdes.Integer()))
                    .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(30)))
                    .reduce(Integer::sum, Materialized.<String, Integer, WindowStore<Bytes, byte[]>>as(WINDOWSTORE_NAME_PRODUCT)
                            .withKeySerde(Serdes.String())
                            .withValueSerde(Serdes.Integer()));

            // Write debugging print here
            quantitiesByProduct.toStream()
                    .print(Printed.<Windowed<String>, Integer>toSysOut().withLabel("Quantities by product"));
        };
    }
    @Bean
    public Consumer<KStream<String, OrderCreatedEvent>> receivedOrderCreatedEventForBrand() {
        return inputStream -> {
            KTable<Windowed<String>, String> quantityAndRevenueByBrand = inputStream
                    .flatMap((key, orderCreatedEvent) -> {
                        return orderCreatedEvent.getOrderItemEvents()
                                .stream()
                                .map(orderItem -> {
                                    String brand = orderItem.getBrand();
                                    Integer quantity = orderItem.getQuantity();
                                    Double totalPrice = orderItem.getQuantity() * orderItem.getPrice();
                                    String data = quantity + "-" + totalPrice;
                                    return KeyValue.pair(brand, data);
                                })
                                .toList();
                    })
                    .groupByKey(Grouped.with(Serdes.String(), Serdes.String()))
                    .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(30)))
                    .reduce((existing, newValue) -> {
                        String[] existingParts = existing.split("-");
                        String[] newParts = newValue.split("-");

                        Integer totalQuantity = Integer.parseInt(existingParts[0].trim()) + Integer.parseInt(newParts[0].trim());
                        Double totalRevenue = Double.parseDouble(existingParts[1].trim()) + Double.parseDouble(newParts[1].trim());
                        return totalQuantity + "-" + totalRevenue;
                    }, Materialized.<String, String, WindowStore<Bytes, byte[]>>as(WINDOWSTORE_NAME_BRAND)
                            .withKeySerde(Serdes.String())
                            .withValueSerde(Serdes.String()));
            quantityAndRevenueByBrand.toStream()
                    .print(Printed.<Windowed<String>, String>toSysOut().withLabel("Brand Analytics"));
        };
    }
}

package com.backend.ddd.infrastructure.config;

import com.backend.ddd.infrastructure.persistence.client.CartClient;
import com.backend.ddd.infrastructure.persistence.client.OrderClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

public class ClientConfig {
    @Bean
    public WebClient cartWebClient(
            @Value("${services.cart.base-url}") String baseUrl
    ) {
        return WebClient.builder().baseUrl(baseUrl).build();
    }

    @Bean
    public CartClient cartClient(@Qualifier("cartWebClient") WebClient cartWebClient) {
        return new CartClient(cartWebClient);
    }

    @Bean
    public WebClient orderWebClient(
            @Value("${services.order.base-url}") String baseUrl
    ) {
        return WebClient.builder().baseUrl(baseUrl).build();
    }

    @Bean
    public OrderClient orderClient(@Qualifier("orderWebClient") WebClient orderWebClient) {
        return new OrderClient(orderWebClient);
    }
}

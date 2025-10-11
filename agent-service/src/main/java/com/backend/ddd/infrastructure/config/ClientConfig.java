package com.backend.ddd.infrastructure.config;

import com.backend.ddd.infrastructure.persistence.client.OrderClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

    // Config the URL for all other services
    @Bean
    public WebClient orderWebClient(@Value("${services.order.base-url}") String baseUrl) {
        return WebClient.builder().baseUrl(baseUrl).build();
    }

    @Bean
    public WebClient productWebClient(@Value("${services.product.base-url}") String baseUrl) {
        return WebClient.builder().baseUrl(baseUrl).build();
    }

    @Bean
    public WebClient cartWebClient(@Value("${services.cart.base-url}") String baseUrl) {
        return WebClient.builder().baseUrl(baseUrl).build();
    }

    //Config the Client to get the base-url
    @Bean
    public OrderClient orderClient(@Qualifier("orderWebClient") WebClient orderWebClient) {
        return new OrderClient(orderWebClient);
    }


}
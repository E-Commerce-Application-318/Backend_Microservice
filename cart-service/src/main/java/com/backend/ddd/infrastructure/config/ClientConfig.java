package com.backend.ddd.infrastructure.config;

import com.backend.ddd.infrastructure.persistence.client.ProductClient;
import com.backend.ddd.infrastructure.persistence.client.ShopClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {
    @Bean
    public WebClient productWebClient(
            @Value("${services.product.base-url}") String baseUrl
    ) {
        return WebClient.builder().baseUrl(baseUrl).build();
    }

    @Bean
    public ProductClient productClient(WebClient productWebClient) {
        return new ProductClient(productWebClient);
    }

    @Bean
    public WebClient shopWebClient(
            @Value("${services.shop.base-url}") String baseUrl
    ) {
        return WebClient.builder().baseUrl(baseUrl).build();
    }


    @Bean
    public ShopClient shopClient(WebClient shopWebClient) {
        return new ShopClient(shopWebClient);
    }
}

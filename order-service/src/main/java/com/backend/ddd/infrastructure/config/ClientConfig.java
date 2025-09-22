package com.backend.ddd.infrastructure.config;

import com.backend.ddd.infrastructure.persistence.client.AuthClient;
import com.backend.ddd.infrastructure.persistence.client.CartClient;
import com.backend.ddd.infrastructure.persistence.client.ProductClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {
    @Bean
    public WebClient cartWebClient(@Value("${services.cart.base-url}") String baseUrl) {
        return WebClient.builder().baseUrl(baseUrl).build();
    }

    @Bean
    public CartClient cartClient(WebClient cartWebClient) {
        return new CartClient(cartWebClient);
    }

    @Bean
    public WebClient productWebClient(@Value("${services.product.base-url}") String baseUrl) {
        return WebClient.builder().baseUrl(baseUrl).build();
    }

    @Bean
    public ProductClient productClient(WebClient productWebClient) {
        return new ProductClient(productWebClient);
    }

    @Bean
    WebClient authWebClient(@Value("${services.auth.base-url}") String baseUrl) {
        return WebClient.builder().baseUrl(baseUrl).build();
    }

    @Bean
    AuthClient authClient(WebClient authWebClient) {
        return new AuthClient(authWebClient);
    }
}

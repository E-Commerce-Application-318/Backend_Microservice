package com.backend.ddd.infrastructure.config;

import com.backend.ddd.infrastructure.persistence.client.ProductClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {
    
    @Bean
    public WebClient productWebClient(
            @Value("${services.product.base-url}") String BaseUrl
    ) {
        return WebClient.builder().baseUrl(BaseUrl).build();
    }

    @Bean
    public ProductClient productClient(WebClient productWebClient) {
        return new ProductClient(productWebClient);
    }
}

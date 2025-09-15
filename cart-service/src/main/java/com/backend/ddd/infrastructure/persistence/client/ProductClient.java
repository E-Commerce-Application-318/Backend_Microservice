package com.backend.ddd.infrastructure.persistence.client;

import com.backend.ddd.infrastructure.persistence.client.model.ExternalApiResponse;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalProduct;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

public class ProductClient {

    private final WebClient productWebClient;

    public ProductClient(WebClient productWebClient) {
        this.productWebClient = productWebClient;
    }

    public ExternalProduct getProductById(UUID productId) {
        ExternalApiResponse<ExternalProduct> externalApiResponse = productWebClient.get()
                .uri("/product-detail/{productId}", productId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ExternalApiResponse<ExternalProduct>>() {})
                .block();
        return externalApiResponse != null ? externalApiResponse.getData() : null;
    }
}

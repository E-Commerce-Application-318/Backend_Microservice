package com.backend.ddd.infrastructure.persistence.client;

import com.backend.ddd.infrastructure.persistence.client.model.ExternalApiResponse;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalProductResponse;
import org.hibernate.query.Order;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class ProductClient {
    private final WebClient productWebClient;

    public ProductClient(WebClient productWebClient) {
        this.productWebClient = productWebClient;
    }

    public List<ExternalProductResponse> getAllProducts() {
        ExternalApiResponse<List<ExternalProductResponse>> externalApiResponse = productWebClient.get()
                .uri("/all-products")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ExternalApiResponse<List<ExternalProductResponse>>>() {})
                .block();
        return externalApiResponse != null ? externalApiResponse.getData() : null;
    }
}
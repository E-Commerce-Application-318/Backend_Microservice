package com.backend.ddd.infrastructure.persistence.client;

import com.backend.ddd.infrastructure.persistence.client.model.ExternalApiResponse;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalProduct;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
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

    public List<ExternalProduct> getProductsByProductIds(List<UUID> productIds) {

        ExternalApiResponse<List<ExternalProduct>> externalApiResponse = productWebClient.post()
                .uri("/product-detail-list")
                .bodyValue(productIds)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ExternalApiResponse<List<ExternalProduct>>>() {})
                .block();
        return externalApiResponse != null ? externalApiResponse.getData() : List.of();
    }
}

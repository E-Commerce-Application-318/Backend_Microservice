package com.backend.ddd.infrastructure.persistence.client;

import com.backend.ddd.infrastructure.persistence.client.model.ExternalApiResponse;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalCartRequest;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalCartResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

public class CartClient {
    private final WebClient cartWebClient;

    public CartClient(WebClient cartWebClient) {
        this.cartWebClient = cartWebClient;
    }

    /**
     *
     * @param userId, productId, quantity
     * @return externalCartResponse
     */
    public ExternalCartResponse addProductToCart(UUID userId, UUID productId, Integer quantity) {
        ExternalApiResponse<ExternalCartResponse> externalApiResponse = cartWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/{userId}/add-item")
                        .build(userId))
                .bodyValue(new ExternalCartRequest(productId, quantity))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ExternalApiResponse<ExternalCartResponse>>() {})
                .block();
        return externalApiResponse != null ? externalApiResponse.getData() : null;
    }

}

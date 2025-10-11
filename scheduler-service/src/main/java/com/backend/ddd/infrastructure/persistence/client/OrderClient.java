package com.backend.ddd.infrastructure.persistence.client;

import com.backend.ddd.infrastructure.persistence.client.model.ExternalApiResponse;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalOrderResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

public class OrderClient {
    private final WebClient orderWebClient;

    public OrderClient(WebClient orderWebClient) {
        this.orderWebClient = orderWebClient;
    }

    public ExternalOrderResponse createOrder(UUID userId, List<UUID> cartIds) {
        ExternalApiResponse<ExternalOrderResponse> externalApiResponse = orderWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/{userId}/create-order")
                        .build(userId))
                .bodyValue(cartIds)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ExternalApiResponse<ExternalOrderResponse>>() {})
                .block();
        return externalApiResponse != null ? externalApiResponse.getData() : null;
    }
}

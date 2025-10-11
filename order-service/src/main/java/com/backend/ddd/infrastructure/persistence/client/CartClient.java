package com.backend.ddd.infrastructure.persistence.client;

import com.backend.ddd.infrastructure.persistence.client.model.ExternalApiResponse;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalCartResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

public class CartClient {

    private final WebClient cartWebClient;

    public CartClient(WebClient cartWebClient) {
        this.cartWebClient = cartWebClient;
    }

    /**
     * get cart details by list of cartIds
     * @param cartIds
     * @return
     */
    public List<ExternalCartResponse> getCartsByCartIds(List<UUID> cartIds) {
        ExternalApiResponse<List<ExternalCartResponse>> externalApiResponse = cartWebClient.post()
                .uri("/get-carts-by-cart-ids")
                .bodyValue(cartIds)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ExternalApiResponse<List<ExternalCartResponse>>>() {})
                .block();
        return externalApiResponse != null ? externalApiResponse.getData() : null;
    }
}

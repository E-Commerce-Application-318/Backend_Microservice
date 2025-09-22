package com.backend.ddd.infrastructure.persistence.client;

import com.backend.ddd.infrastructure.persistence.client.model.ExternalApiResponse;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalProduct;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CartClient {

    private final WebClient cartWebClient;

    public CartClient(WebClient cartWebClient) {
        this.cartWebClient = cartWebClient;
    }

    public Map<UUID, Integer> getProductIdsAndQuantitiesByCartIds(List<UUID> cartIds) {
        ExternalApiResponse<Map<UUID, Integer>> externalApiResponse = cartWebClient.post()
                .uri("/get-productids-quantity-by-cart-ids")
                .bodyValue(cartIds)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ExternalApiResponse<Map<UUID, Integer>>>() {})
                .block();
        return externalApiResponse != null ? externalApiResponse.getData() : null;
    }

}

package com.backend.ddd.infrastructure.persistence.client;

import com.backend.ddd.infrastructure.persistence.client.model.ExternalApiResponse;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalShop;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;


public class ShopClient {

    private final WebClient shopWebClient;

    public ShopClient(WebClient shopWebClient) {
        this.shopWebClient = shopWebClient;
    }

    public ExternalShop getShopDetailByShopId(UUID shopId) {
        ExternalApiResponse<ExternalShop> externalApiResponse = shopWebClient.get()
                .uri("/{shopId}/shop-detail", shopId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ExternalApiResponse<ExternalShop>>() {})
                .block();
        return externalApiResponse != null ? externalApiResponse.getData() : null;
    }

}

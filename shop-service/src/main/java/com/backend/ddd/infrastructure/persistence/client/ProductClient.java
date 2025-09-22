package com.backend.ddd.infrastructure.persistence.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;
@Slf4j
public class ProductClient {

    private final WebClient productWebClient;

    public ProductClient(WebClient productWebClient) {
        this.productWebClient = productWebClient;
    }

    public List<ExternalProduct> getAllProductsByShopId(UUID shopId) {
        ExternalApiResponse<List<ExternalProduct>> externalApiResponse = productWebClient.get()
                .uri("/shop/{shopId}/all-products", shopId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ExternalApiResponse<List<ExternalProduct>>>() {})
                .block();
        log.info("External API Response: {}", externalApiResponse);
        return externalApiResponse != null ? externalApiResponse.getData() : List.of();
    }

    // Helper type for WebClient generic binding
    // private static class ExternalApiResponseDTOList extends ExternalApiResponse<List<ExternalProduct>> {}
}

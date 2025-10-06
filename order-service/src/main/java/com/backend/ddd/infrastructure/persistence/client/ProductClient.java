package com.backend.ddd.infrastructure.persistence.client;

import com.backend.ddd.infrastructure.persistence.client.model.ExternalApiResponse;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalProduct;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ProductClient {
    private final WebClient productWebClient;

    public ProductClient(WebClient productWebClient) {
        this.productWebClient = productWebClient;
    }

    /**
     * get product details by list of productIds
     * @param productIds
     * @return
     */
    public List<ExternalProduct> getProductsByProductIds(List<UUID> productIds) {
        ExternalApiResponse<List<ExternalProduct>> externalApiResponse = productWebClient.post()
                .uri("/product-detail-list")
                .bodyValue(productIds)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ExternalApiResponse<List<ExternalProduct>>>() {})
                .block();
        return externalApiResponse != null ? externalApiResponse.getData() : List.of();
    }

//    public Boolean processOrder(Map<UUID, Integer> productIdsAndQuantities) {
//        ExternalApiResponse<Boolean> externalApiResponse = productWebClient.post()
//                .uri("/process-order")
//                .bodyValue(productIdsAndQuantities)
//                .retrieve()
//                .bodyToMono(new ParameterizedTypeReference<ExternalApiResponse<Boolean>>() {})
//                .block();
//        return externalApiResponse != null && Boolean.TRUE.equals(externalApiResponse.getData());
//    }
}

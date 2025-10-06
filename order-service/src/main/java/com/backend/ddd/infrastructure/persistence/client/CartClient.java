package com.backend.ddd.infrastructure.persistence.client;

import com.backend.ddd.infrastructure.persistence.client.model.ExternalApiResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CartClient {

    private final WebClient cartWebClient;

    public CartClient(WebClient cartWebClient) {
        this.cartWebClient = cartWebClient;
    }

    /**
     * get productIds and quantities that are selected (input by request) in the cart
     * @param cartIds
     * @return
     */
    public Map<UUID, Integer> getProductIdsAndQuantitiesByCartIds(List<UUID> cartIds) {
        ExternalApiResponse<Map<UUID, Integer>> externalApiResponse = cartWebClient.post()
                .uri("/get-productids-quantity-by-cart-ids")
                .bodyValue(cartIds)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ExternalApiResponse<Map<UUID, Integer>>>() {})
                .block();
        return externalApiResponse != null ? externalApiResponse.getData() : null;
    }

//    public Boolean removeCartsByCartIds(List<UUID> cartIds) {
//        ExternalApiResponse<Boolean> externalApiResponse = cartWebClient.method(HttpMethod.DELETE)
//                .uri("/remove-carts")
//                .bodyValue(cartIds)
//                .retrieve()
//                .bodyToMono(new ParameterizedTypeReference<ExternalApiResponse<Boolean>>() {})
//                .block();
//        return externalApiResponse != null && externalApiResponse.getData();
//    }

}

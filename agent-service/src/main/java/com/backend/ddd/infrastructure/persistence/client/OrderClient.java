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

    // get all orders of user ID
    public List<ExternalOrderResponse> getAllOrders(UUID userId){
        ExternalApiResponse<List<ExternalOrderResponse>> externalApiResponse = orderWebClient.get()
                .uri("/{userId}/get-all-orders", userId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ExternalApiResponse<List<ExternalOrderResponse>>>(){})
                .block();
        return externalApiResponse != null ? externalApiResponse.getData() : null;
    }


}

package com.backend.ddd.infrastructure.persistence.client;

import com.backend.ddd.infrastructure.persistence.client.model.ExternalApiResponse;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalOrderResponse;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalUpdateOrderRequest;
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

    public ExternalOrderResponse createOrder(
            UUID userId, List<UUID> cartIds
    ) {
        ExternalApiResponse<ExternalOrderResponse> externalApiResponse = orderWebClient.post()
                .uri("/{userId}/create-order", userId)
                .bodyValue(cartIds)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ExternalApiResponse<ExternalOrderResponse>>() {})
                .block();
        return externalApiResponse != null ? externalApiResponse.getData() : null;
    }

    public String updateOrder(
            UUID orderId, String address, String phoneNumber
    ) {
        ExternalUpdateOrderRequest updateOrderRequest = new ExternalUpdateOrderRequest(orderId, address, phoneNumber);
        ExternalApiResponse<String> externalApiResponse = orderWebClient.put()
                .uri("/update-order")
                .bodyValue(updateOrderRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ExternalApiResponse<String>>() {})
                .block();
        return externalApiResponse != null ? externalApiResponse.getData() : "Failure";
    }

    public String cancelOrder(UUID orderId) {
        ExternalApiResponse<String> externalApiResponse = orderWebClient.put()
                .uri("/cancel-order/{orderId}", orderId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ExternalApiResponse<String>>() {})
                .block();
        return externalApiResponse != null ? externalApiResponse.getData() : "Failure";


    }
}

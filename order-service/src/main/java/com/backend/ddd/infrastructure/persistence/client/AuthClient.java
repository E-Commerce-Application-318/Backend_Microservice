package com.backend.ddd.infrastructure.persistence.client;

import com.backend.ddd.controller.model.dto.PaymentRequestDTO;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalApiResponse;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalUser;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

public class AuthClient {
    private final WebClient authWebClient;

    public AuthClient(WebClient authWebClient) {
        this.authWebClient = authWebClient;
    }

    public ExternalUser getAddressByUserId(UUID userID) {
        ExternalApiResponse<ExternalUser> externalApiResponse = authWebClient.get()
                .uri("/{userId}/get-address", userID)
                .retrieve()
                .bodyToMono(new org.springframework.core.ParameterizedTypeReference<ExternalApiResponse<ExternalUser>>() {})
                .block();
        return externalApiResponse != null ? externalApiResponse.getData() : null;
    }

    public Boolean paymentOrder(UUID userId, PaymentRequestDTO paymentRequestDTO) {
        ExternalApiResponse<Boolean> externalApiResponse = authWebClient.post()
                .uri("/{userId}/confirm_payment", userId)
                .bodyValue(paymentRequestDTO)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ExternalApiResponse<Boolean>>() {})
                .block();
        return externalApiResponse != null; // true when payment request same as payment of user ID saved in database
    }
}

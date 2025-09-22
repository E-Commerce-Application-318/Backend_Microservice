package com.backend.ddd.infrastructure.persistence.client;

import com.backend.ddd.infrastructure.persistence.client.model.ExternalApiResponse;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalUser;
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

}

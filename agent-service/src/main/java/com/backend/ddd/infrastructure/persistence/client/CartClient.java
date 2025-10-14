package com.backend.ddd.infrastructure.persistence.client;

import org.springframework.web.reactive.function.client.WebClient;

public class CartClient {
    private final WebClient cartWebClient;

    public CartClient(WebClient cartWebClient) {
        this.cartWebClient = cartWebClient;
    }
}

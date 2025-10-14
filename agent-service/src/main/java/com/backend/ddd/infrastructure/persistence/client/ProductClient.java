package com.backend.ddd.infrastructure.persistence.client;

import com.backend.ddd.infrastructure.persistence.client.model.ExternalProductResponse;
import org.hibernate.query.Order;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class ProductClient {
    private final WebClient productWebClient;

    public ProductClient(WebClient productWebClient) {
        this.productWebClient = productWebClient;
    }

//    pub
}
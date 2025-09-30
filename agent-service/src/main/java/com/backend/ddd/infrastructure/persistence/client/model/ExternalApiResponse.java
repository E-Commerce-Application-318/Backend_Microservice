package com.backend.ddd.infrastructure.persistence.client.model;

import lombok.Data;

@Data
public class ExternalApiResponse <T> {
    private String success;
    private String message;
    private T data;
}

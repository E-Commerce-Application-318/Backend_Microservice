package com.backend.ddd.infrastructure.persistence.client.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class ExternalApiResponse <T> {
    private String success;
    private String message;
    private T data;
}

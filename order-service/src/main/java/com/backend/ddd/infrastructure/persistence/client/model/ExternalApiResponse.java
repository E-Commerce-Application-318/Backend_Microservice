package com.backend.ddd.infrastructure.persistence.client.model;

import lombok.*;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain=true)
public class ExternalApiResponse <T> {
    private String success;
    private String message;
    private T data;
}

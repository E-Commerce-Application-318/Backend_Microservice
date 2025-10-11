package com.backend.ddd.infrastructure.persistence.client.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class ExternalUserResponse {
    private String name;
    private String address;
    private String phoneNumber;
}

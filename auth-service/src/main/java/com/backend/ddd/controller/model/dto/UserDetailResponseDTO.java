package com.backend.ddd.controller.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class UserDetailResponseDTO {
    private String address;
    private String phoneNumber;
}

package com.backend.ddd.controller.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailResponseDTO {
    private String name;
    private String address;
    private String phoneNumber;
}

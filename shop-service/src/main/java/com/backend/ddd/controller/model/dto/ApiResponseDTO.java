package com.backend.ddd.controller.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class ApiResponseDTO <T> {
    private Boolean success;
    private String message;
    private T Data;

    public static <T> ApiResponseDTO<T> success(String message, T data) {
        return new ApiResponseDTO<T>()
                .setSuccess(true)
                .setMessage(message)
                .setData(data);
    }

    public static <T> ApiResponseDTO<T> error(String message) {
        return new ApiResponseDTO<T>()
                .setSuccess(false)
                .setMessage(message);
    }
}


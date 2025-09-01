package com.backend.ddd.controller.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain=true)
public class ApiResponseDTO<T> {
    boolean success;
    String message;
    private T data;
    private LocalDateTime timestamp;

    public static <T> ApiResponseDTO<T> success(String message, T data) {
        return new ApiResponseDTO<T>()
                .setSuccess(true)
                .setMessage(message)
                .setData(data)
                .setTimestamp(LocalDateTime.now());
    }

    public static <T> ApiResponseDTO<T> error(String message) {
        return new ApiResponseDTO<T>()
                .setSuccess(false)
                .setMessage(message)
                .setData(null)
                .setTimestamp(LocalDateTime.now());
    }
}

package com.darcode.curalink.dto;

import java.time.LocalDateTime;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data,
        LocalDateTime timestamp

) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                true,
                "Ok",
                data,
                LocalDateTime.now()
        );
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(
                true,
                message,
                data,
                LocalDateTime.now()
        );
    }
}

package com.example.NIS.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private int statusCode;
    private String timestamp;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                true,
                "Operation successful",
                data,
                HttpStatus.OK.value(),
                java.time.LocalDateTime.now().toString()
        );
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(
                true,
                message,
                data,
                HttpStatus.OK.value(),
                java.time.LocalDateTime.now().toString()
        );
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(
                false,
                message,
                null,
                HttpStatus.BAD_REQUEST.value(),
                java.time.LocalDateTime.now().toString()
        );
    }

    public static <T> ApiResponse<T> error(String message, HttpStatus status) {
        return new ApiResponse<>(
                false,
                message,
                null,
                status.value(),
                java.time.LocalDateTime.now().toString()
        );
    }
}
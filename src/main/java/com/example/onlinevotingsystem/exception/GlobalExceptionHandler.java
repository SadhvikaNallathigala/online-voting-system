package com.example.onlinevotingsystem.exception;

import com.example.onlinevotingsystem.response.ApiResponse;
import com.example.onlinevotingsystem.response.MetaResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<Object> handleIllegalArgumentException(
            IllegalArgumentException exception) {

        return new ApiResponse<>(
                false,
                null,
                exception.getMessage(),
                new MetaResponse(
                        LocalDateTime.now(),
                        "Request failed"
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Object> handleException(
            Exception exception) {

        return new ApiResponse<>(
                false,
                null,
                exception.getMessage(),
                new MetaResponse(
                        LocalDateTime.now(),
                        "Internal server error"
                )
        );
    }
}
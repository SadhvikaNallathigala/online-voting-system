package com.example.onlinevotingsystem.response;

import lombok.Data;

@Data
public class ApiResponse<T> {

    private boolean success;
    private T data;
    private Object error;
    private MetaResponse meta;

    public ApiResponse(
            boolean success,
            T data,
            Object error,
            MetaResponse meta) {

        this.success = success;
        this.data = data;
        this.error = error;
        this.meta = meta;
    }
}
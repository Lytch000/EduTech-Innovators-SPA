package com.EduTech.utils;

import lombok.Data;

@Data
public class ApiResponse<T> {
    public ApiResponse(T data, int status) {
        this.status = status;
        this.data = data;
    }

    protected int status;
    protected T data;
} 
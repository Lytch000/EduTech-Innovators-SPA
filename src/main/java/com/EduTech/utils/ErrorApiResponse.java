package com.EduTech.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ErrorApiResponse extends ApiResponse<String> {
    public ErrorApiResponse(String errorMessage) {
        super(errorMessage, 400);
    }

    public ErrorApiResponse(String errorMessage, int status) {
        super(errorMessage, status);
    }
} 
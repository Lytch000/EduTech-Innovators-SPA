package com.EduTech.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ErrorApiResponse is a specialized version of ApiResponse used to handle error responses in the API.
 *
 * @param <T> the type of data being returned in the response
 * @author Franco Carrasco
 * @version 1.0
 * 
 * @apiNote This class is only used for Franco Carrasco's API endpoints (The rest of the equipment has nothing to do with this).
 */
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

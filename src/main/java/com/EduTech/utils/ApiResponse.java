package com.EduTech.utils;

import lombok.Data;

/**
 * ApiResponse is a generic class used to standardize API responses.
 * It contains a status code and the data to be returned.
 *
 * @param <T> the type of data being returned in the response
 * @author Franco Carrasco
 * @version 1.0
 * 
 * @apiNote This class is only used for Franco Carrasco's API endpoints (The rest of the equipment has nothing to do with this.).
 */
@Data
public class ApiResponse<T> {
    public ApiResponse(T data, int status) {
        this.status = status;
        this.data = data;
    }

    protected int status;
    protected T data;
}

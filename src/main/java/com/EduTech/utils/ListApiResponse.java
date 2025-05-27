package com.EduTech.utils;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ListApiResponse is a specialized version of ApiResponse used to handle responses that return a list of data.
 *
 * @param <T> the type of data being returned in the response
 * @author Franco Carrasco
 * @version 1.0
 * 
 * @apiNote This class is only used for Franco Carrasco's API endpoints (The rest of the equipment has nothing to do with this.).
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ListApiResponse<T> extends ApiResponse<List<T>> {
    public ListApiResponse(List<T> data) {
        super(data, 200);

        this.count = data.size();
    }

    private int count;
}

package com.EduTech.utils;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ListApiResponse<T> extends ApiResponse<List<T>> {
    public ListApiResponse(List<T> data) {
        super(data, 200);

        this.count = data.size();
    }

    private int count;
}

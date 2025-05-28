package com.EduTech.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ListApiResponse<T> extends ApiResponse<List<T>> {
    public ListApiResponse(List<T> data) {
        super(data, 200);
        this.count = data.size();
    }

    private int count;
} 
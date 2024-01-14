package com.simplewebserver.processor;

import com.simplewebserver.filter.Filter;
import lombok.Data;


@Data
public class FilterHolder {
    private Filter filter;
    private String filterClass;

    public FilterHolder(String filterClass) {
        this.filterClass = filterClass;
    }
}

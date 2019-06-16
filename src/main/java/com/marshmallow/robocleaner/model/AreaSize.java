package com.marshmallow.robocleaner.model;

import lombok.Value;

import java.util.List;

/**
 * Holds the dimensions of the Area.
 *
 */
@Value
public class AreaSize {

    private Integer m;
    private Integer n;

    public AreaSize(List<Integer> values) {
        m = values.get(0);
        n = values.get(1);
    }
}

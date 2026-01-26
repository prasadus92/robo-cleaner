package com.marshmallow.robocleaner.model;

import java.util.List;

/**
 * Immutable record representing the dimensions of the cleaning area.
 *
 * @param width  the width of the area (number of columns)
 * @param height the height of the area (number of rows)
 */
public record Dimensions(int width, int height) {

    /**
     * Create Dimensions ensuring both values are positive.
     */
    public Dimensions {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Dimensions must be positive: width=" + width + ", height=" + height);
        }
    }

    /**
     * Create Dimensions from a list of integers.
     *
     * @param values list containing [width, height]
     * @return a new Dimensions instance
     * @throws IllegalArgumentException if values is invalid
     */
    public static Dimensions fromList(List<Integer> values) {
        if (values == null || values.size() != 2) {
            throw new IllegalArgumentException("Dimensions must have exactly 2 values");
        }
        return new Dimensions(values.get(0), values.get(1));
    }

    /**
     * Check if a coordinate is within these dimensions.
     *
     * @param coordinate the coordinate to check
     * @return true if the coordinate is within bounds
     */
    public boolean contains(Coordinate coordinate) {
        return coordinate.isWithinBounds(width, height);
    }
}

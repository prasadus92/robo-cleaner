package com.marshmallow.robocleaner.model;

import java.util.List;

/**
 * Immutable record representing a 2D coordinate point.
 * Uses Java record for automatic equals, hashCode, and toString.
 *
 * @param x the x-coordinate (column)
 * @param y the y-coordinate (row)
 */
public record Coordinate(int x, int y) {

    /**
     * Create a Coordinate from a list of integers.
     *
     * @param values list containing [x, y] coordinates
     * @return a new Coordinate instance
     * @throws IllegalArgumentException if values is null or doesn't contain exactly 2 elements
     */
    public static Coordinate fromList(List<Integer> values) {
        if (values == null || values.size() != 2) {
            throw new IllegalArgumentException("Coordinate must have exactly 2 values");
        }
        return new Coordinate(values.get(0), values.get(1));
    }

    /**
     * Create a new coordinate by applying a direction movement.
     *
     * @param direction the direction to move
     * @return a new Coordinate after applying the movement
     */
    public Coordinate move(Direction direction) {
        return new Coordinate(x + direction.getDeltaX(), y + direction.getDeltaY());
    }

    /**
     * Check if this coordinate is within the given bounds.
     *
     * @param width  the maximum x value (exclusive)
     * @param height the maximum y value (exclusive)
     * @return true if the coordinate is within bounds
     */
    public boolean isWithinBounds(int width, int height) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * Convert to a list representation for JSON serialization.
     *
     * @return list containing [x, y]
     */
    public List<Integer> toList() {
        return List.of(x, y);
    }
}

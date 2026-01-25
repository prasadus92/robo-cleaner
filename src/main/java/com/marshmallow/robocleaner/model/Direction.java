package com.marshmallow.robocleaner.model;

/**
 * Cardinal directions for navigation.
 * Each direction has a delta x and delta y for movement calculation.
 */
public enum Direction {
    N(0, 1),   // North: y increases
    S(0, -1),  // South: y decreases
    E(1, 0),   // East: x increases
    W(-1, 0);  // West: x decreases

    private final int deltaX;
    private final int deltaY;

    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }

    /**
     * Parse a character to a Direction.
     *
     * @param c the character representing a direction (N, S, E, W)
     * @return the corresponding Direction
     * @throws IllegalArgumentException if the character is not a valid direction
     */
    public static Direction fromChar(char c) {
        return switch (c) {
            case 'N' -> N;
            case 'S' -> S;
            case 'E' -> E;
            case 'W' -> W;
            default -> throw new IllegalArgumentException("Invalid direction: " + c);
        };
    }
}

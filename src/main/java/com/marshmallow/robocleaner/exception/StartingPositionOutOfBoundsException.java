package com.marshmallow.robocleaner.exception;

import com.marshmallow.robocleaner.model.Coordinate;
import com.marshmallow.robocleaner.model.Dimensions;

/**
 * Exception thrown when the starting position is outside the area bounds.
 */
public class StartingPositionOutOfBoundsException extends NavigationException {

    public StartingPositionOutOfBoundsException(Coordinate position, Dimensions dimensions) {
        super(String.format(
                "Starting position %s is outside the area bounds (0,0) to (%d,%d)",
                position, dimensions.width() - 1, dimensions.height() - 1
        ));
    }
}

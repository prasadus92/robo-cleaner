package com.marshmallow.robocleaner.exception;

/**
 * Exception thrown when an oil patch coordinate is outside the area bounds.
 */
public class OilPatchOutOfBoundsException extends NavigationException {

    public OilPatchOutOfBoundsException(String message) {
        super(message);
    }
}

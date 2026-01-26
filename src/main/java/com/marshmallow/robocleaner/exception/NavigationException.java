package com.marshmallow.robocleaner.exception;

/**
 * Base exception for navigation-related errors.
 */
public abstract class NavigationException extends RuntimeException {

    protected NavigationException(String message) {
        super(message);
    }

    protected NavigationException(String message, Throwable cause) {
        super(message, cause);
    }
}

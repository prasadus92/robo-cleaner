package com.marshmallow.robocleaner.exception;

public class StartingPositionOutOfAreaException extends GenericException {
    public StartingPositionOutOfAreaException() {
        super("Starting position coordinates are outside of the area");
    }
}

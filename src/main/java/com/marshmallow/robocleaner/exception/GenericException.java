package com.marshmallow.robocleaner.exception;

public class GenericException extends RuntimeException {

    private static String oilPatchIndexString = " - oil patch index: ";

    public GenericException(String message) {
        super(message);
    }

    public GenericException(String message, int oilPatchIndex) {
        super(message + oilPatchIndexString + oilPatchIndex);
    }

    public GenericException(String message, Exception e) {
        super(message, e);
    }

    public GenericException(String message,
                            int oilPatchIndex,
                            Exception e) {
        super(message + oilPatchIndexString + oilPatchIndex, e);
    }
}

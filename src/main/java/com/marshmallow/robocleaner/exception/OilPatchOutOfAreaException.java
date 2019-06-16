package com.marshmallow.robocleaner.exception;

public class OilPatchOutOfAreaException extends GenericException {

    public OilPatchOutOfAreaException(int oilPatchIndex) {
        super("Oil patch coordinates are outside of the area", oilPatchIndex);
    }

}

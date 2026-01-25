package com.marshmallow.robocleaner.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

/**
 * Validator for the @ValidCoordinate annotation.
 * Checks that the list contains exactly 2 non-negative integers.
 */
public class ValidCoordinateValidator implements ConstraintValidator<ValidCoordinate, List<Integer>> {

    @Override
    public boolean isValid(List<Integer> values, ConstraintValidatorContext context) {
        if (values == null || values.size() != 2) {
            return false;
        }
        Integer x = values.get(0);
        Integer y = values.get(1);
        return x != null && y != null && x >= 0 && y >= 0;
    }
}

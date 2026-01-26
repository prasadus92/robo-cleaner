package com.marshmallow.robocleaner.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

/**
 * Validator for the @PositiveDimensions annotation.
 * Checks that the list contains exactly 2 positive integers.
 */
public class PositiveDimensionsValidator implements ConstraintValidator<PositiveDimensions, List<Integer>> {

    @Override
    public boolean isValid(List<Integer> values, ConstraintValidatorContext context) {
        if (values == null || values.size() != 2) {
            return false;
        }
        Integer width = values.get(0);
        Integer height = values.get(1);
        return width != null && height != null && width > 0 && height > 0;
    }
}

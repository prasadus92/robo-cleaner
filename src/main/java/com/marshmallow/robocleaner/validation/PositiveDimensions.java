package com.marshmallow.robocleaner.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validation annotation for area dimensions.
 * Validates that the value is a list of exactly 2 positive integers.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PositiveDimensionsValidator.class)
public @interface PositiveDimensions {

    String message() default "Area size must be a pair of positive integers [width, height] where both width > 0 and height > 0";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.marshmallow.robocleaner.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validation annotation for coordinate values.
 * Validates that the value is a list of exactly 2 non-negative integers.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidCoordinateValidator.class)
public @interface ValidCoordinate {

    String message() default "Position must be a pair of non-negative integers [x, y] where x >= 0 and y >= 0";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

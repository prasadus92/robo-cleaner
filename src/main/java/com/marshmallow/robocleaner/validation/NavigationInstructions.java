package com.marshmallow.robocleaner.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validation annotation for navigation instructions.
 * Validates that the string contains only valid direction characters (N, S, E, W).
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NavigationInstructionsValidator.class)
public @interface NavigationInstructions {

    String message() default "Navigation instructions must be a non-empty string containing only N, S, E, W characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

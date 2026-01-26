package com.marshmallow.robocleaner.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validation annotation for oil patches list.
 * Validates that each oil patch is a valid coordinate pair with non-negative integers.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OilPatchesValidator.class)
public @interface OilPatches {

    String message() default "Oil patches must be a non-empty list of [x, y] coordinate pairs where x >= 0 and y >= 0";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

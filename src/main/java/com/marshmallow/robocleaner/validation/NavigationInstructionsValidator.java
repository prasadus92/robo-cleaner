package com.marshmallow.robocleaner.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

/**
 * Validator for the @NavigationInstructions annotation.
 * Checks that the string is non-empty and contains only valid direction characters.
 */
public class NavigationInstructionsValidator implements ConstraintValidator<NavigationInstructions, String> {

    private static final Set<Character> VALID_DIRECTIONS = Set.of('N', 'S', 'E', 'W');

    @Override
    public boolean isValid(String instructions, ConstraintValidatorContext context) {
        if (instructions == null || instructions.isEmpty()) {
            return false;
        }

        for (char c : instructions.toCharArray()) {
            if (!VALID_DIRECTIONS.contains(c)) {
                return false;
            }
        }

        return true;
    }
}

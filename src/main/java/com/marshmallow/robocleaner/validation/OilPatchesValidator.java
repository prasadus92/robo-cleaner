package com.marshmallow.robocleaner.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

/**
 * Validator for the @OilPatches annotation.
 * Checks that each oil patch is a valid coordinate pair with non-negative integers.
 */
public class OilPatchesValidator implements ConstraintValidator<OilPatches, List<List<Integer>>> {

    @Override
    public boolean isValid(List<List<Integer>> oilPatches, ConstraintValidatorContext context) {
        if (oilPatches == null || oilPatches.isEmpty()) {
            return false;
        }

        for (List<Integer> patch : oilPatches) {
            if (!isValidCoordinate(patch)) {
                return false;
            }
        }

        return true;
    }

    private boolean isValidCoordinate(List<Integer> coordinates) {
        if (coordinates == null || coordinates.size() != 2) {
            return false;
        }
        Integer x = coordinates.get(0);
        Integer y = coordinates.get(1);
        return x != null && y != null && x >= 0 && y >= 0;
    }
}

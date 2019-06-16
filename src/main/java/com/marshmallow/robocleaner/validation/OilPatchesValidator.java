package com.marshmallow.robocleaner.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class OilPatchesValidator implements ConstraintValidator<OilPatches, List<List<Integer>>> {
    @Override
    public void initialize(OilPatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(List<List<Integer>> oilPatches, ConstraintValidatorContext constraintValidatorContext) {
        if (oilPatches == null || oilPatches.isEmpty()) {
            return false;
        }

        return !oilPatches.stream().anyMatch(integers -> integers.size() != 2);
    }
}

package com.marshmallow.robocleaner.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class OilPatchesValidator implements ConstraintValidator<OilPatches, List<List<Integer>>> {
    @Override
    public boolean isValid(List<List<Integer>> oilPatches, ConstraintValidatorContext constraintValidatorContext) {
        // Non-null + Not empty + Pairs + Positive Values
        return (oilPatches != null) && (!oilPatches.isEmpty()) &&
               (!oilPatches.stream().anyMatch(integers -> (integers.size() != 2) || (integers.get(0) < 0) || (integers.get(1) < 0)));
    }
}

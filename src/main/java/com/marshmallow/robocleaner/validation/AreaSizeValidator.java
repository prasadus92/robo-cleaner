package com.marshmallow.robocleaner.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class AreaSizeValidator implements ConstraintValidator<AreaSize, List<Integer>> {
    @Override
    public void initialize(AreaSize constraintAnnotation) {

    }

    @Override
    public boolean isValid(List<Integer> values, ConstraintValidatorContext constraintValidatorContext) {
        return values != null && values.size() == 2 && values.get(0) > 0 && values.get(1) > 0;
    }
}

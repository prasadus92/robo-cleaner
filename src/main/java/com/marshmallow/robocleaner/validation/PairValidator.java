package com.marshmallow.robocleaner.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class PairValidator implements ConstraintValidator<Pair, List<Integer>> {
    @Override
    public void initialize(Pair constraintAnnotation) {

    }

    @Override
    public boolean isValid(List<Integer> values, ConstraintValidatorContext constraintValidatorContext) {
        return values.size() == 2;
    }
}

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
        // Non-null + Pair + Positive Values
        return (values != null) && (values.size() == 2) && (values.get(0) >= 0) && (values.get(1) >= 0);
    }
}

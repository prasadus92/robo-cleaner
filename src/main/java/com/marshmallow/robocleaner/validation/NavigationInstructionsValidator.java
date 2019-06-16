package com.marshmallow.robocleaner.validation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NavigationInstructionsValidator implements ConstraintValidator<NavigationInstructions, String> {
    @Override
    public void initialize(NavigationInstructions constraintAnnotation) {

    }

    @Override
    public boolean isValid(String navigationInstructions, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(navigationInstructions)) {
            return false;
        }

        boolean isValid = true;
        for (int i = 0; i < navigationInstructions.length(); i++) {
            switch (navigationInstructions.charAt(i)) {
                case 'N':
                case 'S':
                case 'E':
                case 'W':
                    // We're good
                    continue;
                default:
                    isValid = false;
                    break;
            }
        }
        return isValid;
    }
}

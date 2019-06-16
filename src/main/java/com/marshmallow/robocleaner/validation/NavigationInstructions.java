package com.marshmallow.robocleaner.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target ({ElementType.METHOD, ElementType.FIELD})
@Retention (RetentionPolicy.RUNTIME)
@Constraint (validatedBy = NavigationInstructionsValidator.class)
public @interface NavigationInstructions {

    String message() default "the accepted sequence of characters for navigationInstructions are N, S, E and W (indicative of directions)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

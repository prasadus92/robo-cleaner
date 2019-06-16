package com.marshmallow.robocleaner.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target ({ElementType.METHOD, ElementType.FIELD})
@Retention (RetentionPolicy.RUNTIME)
@Constraint (validatedBy = AreaSizeValidator.class)
public @interface AreaSize {

    String message() default "accepted value for areaSize is a pair of X and Y (X > 0 & Y > 0)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.marshmallow.robocleaner.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target ({ElementType.METHOD, ElementType.FIELD})
@Retention (RetentionPolicy.RUNTIME)
@Constraint (validatedBy = OilPatchesValidator.class)
public @interface OilPatches {

    String message() default "oilPatches must be a list of X and Y pairs (X >= 0 and Y >= 0)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

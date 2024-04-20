package com.kapia.jobboard.api.annotation;

import com.kapia.jobboard.api.constants.OperatingMode;
import com.kapia.jobboard.api.validation.OperatingModeSubsetValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = OperatingModeSubsetValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface OperatingModeSubset {

    OperatingMode[] anyOf();

    String message() default "Operating mode must be any of {anyOf}";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

}

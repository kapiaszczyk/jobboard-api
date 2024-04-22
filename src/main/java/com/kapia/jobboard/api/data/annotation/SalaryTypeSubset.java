package com.kapia.jobboard.api.data.annotation;

import com.kapia.jobboard.api.data.constants.SalaryType;
import com.kapia.jobboard.api.data.validation.SalaryTypeSubsetValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = SalaryTypeSubsetValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)

public @interface SalaryTypeSubset {

    SalaryType[] anyOf();

    String message() default "Salary type must be any of {anyOf}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

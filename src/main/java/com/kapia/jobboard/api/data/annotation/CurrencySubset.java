package com.kapia.jobboard.api.data.annotation;

import com.kapia.jobboard.api.data.validation.CurrencyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The CurrencySubset interface is a constraint annotation that validates that the currency is any of the specified currencies.
 */
@Constraint(validatedBy = CurrencyValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface CurrencySubset {

    String[] anyOf() default {};

    String message() default "Currency must be any of {anyOf}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

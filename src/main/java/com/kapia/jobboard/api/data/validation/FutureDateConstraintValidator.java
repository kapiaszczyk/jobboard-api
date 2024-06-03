package com.kapia.jobboard.api.data.validation;

import com.kapia.jobboard.api.data.annotation.FutureDateConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Date;

/**
 * The FutureDateConstraintValidator class is a validator class that validates the date to be in the future.
 */
public class FutureDateConstraintValidator implements ConstraintValidator<FutureDateConstraint, Date> {

    // 604800000L is 7 days in milliseconds
    private static final long EXPIRES_AT_MIN_PERIOD = 604800000L;


    @Override
    public void initialize(FutureDateConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {

        return date != null && date.getTime() >= System.currentTimeMillis() + EXPIRES_AT_MIN_PERIOD;

    }
}

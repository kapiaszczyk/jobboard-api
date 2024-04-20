package com.kapia.jobboard.api.validation;

import com.kapia.jobboard.api.annotation.FutureDateConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Date;

public class FutureDateConstraintValidator implements ConstraintValidator<FutureDateConstraint, Date> {

    // 604800000L is 7 days in milliseconds
    private static final long EXPIRES_AT_MIN_PERIOD = 604800000L;


    @Override
    public void initialize(FutureDateConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {

        if (date == null || date.getTime() < System.currentTimeMillis() + EXPIRES_AT_MIN_PERIOD) {
            return false;
        }
        return true;

    }
}

package com.kapia.jobboard.api.data.validation;

import com.kapia.jobboard.api.data.annotation.LogoSizeConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LogoSizeValidator implements ConstraintValidator<LogoSizeConstraint, byte[]> {

    private static final int MAX_COMPANY_LOGO_SIZE = 1024 * 1024;

    @Override
    public void initialize(LogoSizeConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(byte[] bytes, ConstraintValidatorContext constraintValidatorContext) {
        return bytes == null || bytes.length <= MAX_COMPANY_LOGO_SIZE;
    }
}

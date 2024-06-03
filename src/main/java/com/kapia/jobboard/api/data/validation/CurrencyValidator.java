package com.kapia.jobboard.api.data.validation;

import com.kapia.jobboard.api.data.annotation.CurrencySubset;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

/**
 * The CurrencyValidator class is a validator class that validates the Currency enum.
 */
public class CurrencyValidator implements ConstraintValidator<CurrencySubset, String> {

    List<String> subset;

    @Override
    public void initialize(CurrencySubset constraintAnnotation) {
        this.subset = Arrays.asList(Arrays.stream(constraintAnnotation.anyOf()[0].split(","))
                .map(String::trim)
                .toArray(String[]::new));
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return subset.contains(s);
    }
}

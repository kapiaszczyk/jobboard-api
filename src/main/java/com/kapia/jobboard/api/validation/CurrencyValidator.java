package com.kapia.jobboard.api.validation;

import com.kapia.jobboard.api.annotation.CurrencySubset;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

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

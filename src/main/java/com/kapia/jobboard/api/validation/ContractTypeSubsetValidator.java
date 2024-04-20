package com.kapia.jobboard.api.validation;

import com.kapia.jobboard.api.annotation.ContractTypeSubset;
import com.kapia.jobboard.api.constants.ContractType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class ContractTypeSubsetValidator implements ConstraintValidator<ContractTypeSubset, ContractType> {

    private ContractType[] subset;

    @Override
    public void initialize(ContractTypeSubset constraint) {
        this.subset = constraint.anyOf();
    }


    @Override
    public boolean isValid(ContractType contractType, ConstraintValidatorContext constraintValidatorContext) {
        return contractType == null || Arrays.asList(subset).contains(contractType);
    }

}

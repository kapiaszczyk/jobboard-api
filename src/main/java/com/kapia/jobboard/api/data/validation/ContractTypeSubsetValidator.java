package com.kapia.jobboard.api.data.validation;

import com.kapia.jobboard.api.data.annotation.ContractTypeSubset;
import com.kapia.jobboard.api.data.constants.ContractType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

/**
 * The ContractTypeSubsetValidator class is a validator class that validates the ContractType enum.
 */
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

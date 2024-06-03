package com.kapia.jobboard.api.data.converters;

import com.kapia.jobboard.api.data.constants.ContractType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * The ContractTypeConverter class is a converter class that converts the ContractType enum to a string and vice versa.
 */
@Converter(autoApply = true)
public class ContractTypeConverter implements AttributeConverter<ContractType, String> {

    @Override
    public String convertToDatabaseColumn(ContractType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public ContractType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return ContractType.fromString(dbData);
    }
}

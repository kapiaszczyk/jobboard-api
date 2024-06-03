package com.kapia.jobboard.api.data.converters;

import com.kapia.jobboard.api.data.constants.SalaryType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * The SalaryTypeConverter class is a converter class that converts the SalaryType enum to a string and vice versa.
 */
@Converter(autoApply = true)
public class SalaryTypeConverter implements AttributeConverter<SalaryType, String> {
    @Override
    public String convertToDatabaseColumn(SalaryType salaryType) {
        if (salaryType == null) {
            return null;
        }
        return salaryType.getValue();
    }

    @Override
    public SalaryType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return SalaryType.fromString(dbData);
    }
}

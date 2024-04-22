package com.kapia.jobboard.api.data.converters;

import com.kapia.jobboard.api.data.constants.OperatingMode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OperatingModeConverter implements AttributeConverter<OperatingMode, String> {

    @Override
    public String convertToDatabaseColumn(OperatingMode attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public OperatingMode convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return OperatingMode.fromString(dbData);
    }

}

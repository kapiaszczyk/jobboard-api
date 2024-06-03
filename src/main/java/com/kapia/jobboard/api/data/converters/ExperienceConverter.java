package com.kapia.jobboard.api.data.converters;

import com.kapia.jobboard.api.data.constants.Experience;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * The ExperienceConverter class is a converter class that converts the Experience enum to a string and vice versa.
 */
@Converter(autoApply = true)
public class ExperienceConverter implements AttributeConverter<Experience, String> {

    @Override
    public String convertToDatabaseColumn(Experience attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public Experience convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Experience.fromString(dbData);
    }


}

package com.kapia.jobboard.api.data.converters;

import com.kapia.jobboard.api.data.constants.DegreeOfKnowledge;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DegreeOfKnowledgeConverter implements AttributeConverter<DegreeOfKnowledge, String> {

    @Override
    public String convertToDatabaseColumn(DegreeOfKnowledge attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public DegreeOfKnowledge convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return DegreeOfKnowledge.fromString(dbData);
    }
}

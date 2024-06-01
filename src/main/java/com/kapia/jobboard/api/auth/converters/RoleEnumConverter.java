package com.kapia.jobboard.api.auth.converters;

import com.kapia.jobboard.api.auth.enums.RoleEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts the RoleEnum attribute to a String value for database storage and vice versa.
 * This converter is used to automatically convert the RoleEnum attribute when persisting
 * and retrieving data from the database.
 */
@Converter(autoApply = true)
public class RoleEnumConverter implements AttributeConverter<RoleEnum, String> {

    /**
     * Converts the RoleEnum attribute to a String value for database storage.
     *
     * @param attribute the RoleEnum attribute to be converted
     * @return the String value representing the RoleEnum attribute
     */
    @Override
    public String convertToDatabaseColumn(RoleEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    /**
     * Converts the String value from the database to a RoleEnum attribute.
     *
     * @param dbData the String value retrieved from the database
     * @return the RoleEnum attribute corresponding to the String value
     */
    @Override
    public RoleEnum convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return RoleEnum.fromString(dbData);
    }

}

package com.kapia.jobboard.api.auth.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Optional;

/**
 * Converts a {@link SimpleGrantedAuthority} object to a String representation and vice versa.
 * This converter is used to persist the authority of a user in the database.
 */
@Converter
public class SimpleGrantedAuthorityConverter implements AttributeConverter<SimpleGrantedAuthority, String> {

    /**
     * Converts a {@link SimpleGrantedAuthority} object to a String representation.
     *
     * @param authority the authority to be converted
     * @return the String representation of the authority
     */
    @Override
    public String convertToDatabaseColumn(SimpleGrantedAuthority authority) {
        return Optional.ofNullable(authority)
                .map(SimpleGrantedAuthority::getAuthority)
                .orElse(null);
    }

    /**
     * Converts a String representation of an authority to a {@link SimpleGrantedAuthority} object.
     *
     * @param role the String representation of the authority
     * @return the {@link SimpleGrantedAuthority} object
     */
    @Override
    public SimpleGrantedAuthority convertToEntityAttribute(String role) {
        return Optional.ofNullable(role)
                .map(SimpleGrantedAuthority::new)
                .orElse(null);
    }
}

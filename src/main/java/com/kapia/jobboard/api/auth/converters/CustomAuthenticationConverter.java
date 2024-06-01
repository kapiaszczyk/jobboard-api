package com.kapia.jobboard.api.auth.converters;

import com.kapia.jobboard.api.data.constants.Defaults;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Converts a Jwt token into an AbstractAuthenticationToken object.
 * This converter is used to extract the roles from the Jwt token and create a collection of GrantedAuthority objects.
 * The JwtAuthenticationToken is then created using the Jwt token and the collection of GrantedAuthority objects.
 */
public class CustomAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    /**
     * Converts the given Jwt token into an AbstractAuthenticationToken object.
     * Extracts the roles from the Jwt token and creates a collection of GrantedAuthority objects.
     * Creates a JwtAuthenticationToken using the Jwt token and the collection of GrantedAuthority objects.
     *
     * @param jwt the Jwt token to convert
     * @return the converted AbstractAuthenticationToken object
     */
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<String> authorities = jwt.getClaimAsStringList("roles");
        Collection<GrantedAuthority> grantedAuthorities = authorities.stream()
                .map(authority -> Defaults.ROLE_PREFIX + authority)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new JwtAuthenticationToken(jwt, grantedAuthorities);
    }
}

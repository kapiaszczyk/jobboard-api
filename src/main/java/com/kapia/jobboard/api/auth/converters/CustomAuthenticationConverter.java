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

public class CustomAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<String> authorities = jwt.getClaimAsStringList("roles");
        Collection<GrantedAuthority> grantedAuthorities = authorities.stream()
                .map(authority -> Defaults.ROLE_PREFIX + authority)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new JwtAuthenticationToken(jwt, grantedAuthorities);
    }
}

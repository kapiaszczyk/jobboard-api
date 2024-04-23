package com.kapia.jobboard.api.auth.util;

import com.kapia.jobboard.api.data.constants.Defaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTUtil.class);

    @Autowired
    JwtEncoder encoder;

    public String generateToken(Authentication authentication) {

        Instant now = Instant.now();
        List<String> scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(Defaults.TOKEN_EXPIRATION_TIME_SECONDS))
                .subject(authentication.getName())
                .claim("roles", scope)
                .build();

        LOGGER.info("Token generated for user {} with expiry time {} ", authentication.getName(), Defaults.TOKEN_EXPIRATION_TIME_SECONDS);

        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}

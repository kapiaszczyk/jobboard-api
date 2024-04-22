package com.kapia.jobboard.api.data.config;

import com.kapia.jobboard.api.auth.service.UserService;
import com.kapia.jobboard.api.data.constants.Defaults;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Collection;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final DataSource dataSource;

    @Autowired
    public SecurityConfig(UserService.CustomUserDetailsService userDetailsService, DataSource dataSource) {
        this.userDetailsService = userDetailsService;
        this.dataSource = dataSource;
    }

    @Value("${jwt.public.key}")
    RSAPublicKey key;

    @Value("${jwt.private.key}")
    RSAPrivateKey priv;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/job-offer/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/company/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/job-offer/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/job-offer/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/company/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/company/**").hasAnyRole("USER", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/actuator/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/job-offer/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/company/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/technology").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/technology/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/actuator/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer((oauth2) -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(new CustomAuthenticationConverter())
                        )
                )
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                );

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.key).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    interface Jwt2AuthoritiesConverter extends Converter<Jwt, Collection<? extends GrantedAuthority>> {
    }

    static class CustomAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
        public AbstractAuthenticationToken convert(Jwt jwt) {
            Collection<String> authorities = jwt.getClaimAsStringList("roles");
            Collection<GrantedAuthority> grantedAuthorities = authorities.stream()
                    .map(authority -> Defaults.ROLE_PREFIX + authority)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            return new JwtAuthenticationToken(jwt, grantedAuthorities);
        }
    }


}

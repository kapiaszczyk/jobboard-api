package com.kapia.jobboard.api.data.filters;

import com.kapia.jobboard.api.auth.controller.AuthController;
import com.kapia.jobboard.api.data.constants.Defaults;
import com.kapia.jobboard.api.data.ratelimiting.RateLimitingService;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private final RateLimitingService rateLimitingService;

    @Autowired
    public RateLimitingFilter(RateLimitingService rateLimitingService) {
        this.rateLimitingService = rateLimitingService;
    }

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {

        if (!rateLimitingService.tryConsume(Defaults.GLOBAL_BUCKET_KEY)) {
            LOGGER.warn("Rate limit exceeded for request {} from IP {} ", request.getRequestURI(), hash(request.getRemoteAddr()));
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            return;
        }

        LOGGER.info("Remaining tokens: {}", rateLimitingService.getRemainingTokens(Defaults.GLOBAL_BUCKET_KEY));

        filterChain.doFilter(request, response);
    }

    public String hash(String str) {

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes());
            return new String(Hex.encode(messageDigest.digest())).substring(0, 8);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Error hashing string: {}", e.getMessage());
            return "ERROR_HASHING_STRING";
        }

    }
}

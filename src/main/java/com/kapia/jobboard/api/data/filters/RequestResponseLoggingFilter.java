package com.kapia.jobboard.api.data.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class RequestResponseLoggingFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        LOGGER.info("Request: {} {} {} {}", httpRequest.getMethod(), httpRequest.getRequestURI(), httpRequest.getQueryString(), hash(httpRequest.getRemoteAddr()));

        filterChain.doFilter(servletRequest, servletResponse);

        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        LOGGER.info("Response: {} {}", httpResponse.getStatus(), httpResponse.getContentType());

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
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

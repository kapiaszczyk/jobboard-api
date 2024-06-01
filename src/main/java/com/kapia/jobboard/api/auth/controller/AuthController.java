package com.kapia.jobboard.api.auth.controller;

import com.kapia.jobboard.api.auth.model.AppUser;
import com.kapia.jobboard.api.auth.request.LoginRequest;
import com.kapia.jobboard.api.auth.request.RegistrationRequest;
import com.kapia.jobboard.api.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * This class is responsible for handling authentication related operations.
 * It provides endpoints for user registration, login and fetching user details.
 */
@Tag(name = "Authentication", description = "Authentication operations")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private UserService authService;

    /**
     * Constructor for the AuthController.
     *
     * @param authService The service to be used for authentication related operations.
     */
    @Autowired
    AuthController(UserService authService) {
        this.authService = authService;
    }

    /**
     * This method is responsible for registering a new user.
     *
     * @param appUser The registration request containing the user details.
     * @return A map containing the response of the registration operation.
     */
    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public Map<String, Object> registerUser(@RequestBody RegistrationRequest appUser) {
        return authService.registerUser(appUser);
    }

    /**
     * This method is responsible for logging in a user.
     *
     * @param loginRequest The login request containing the user credentials.
     * @return A map containing the response of the login operation.
     */
    @Operation(summary = "Login as a user")
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    /**
     * This method is responsible for fetching the details of the logged in user.
     *
     * @return The details of the logged in user.
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get user details")
    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppUser getUserDetails() {
        return authService.getUserDetails();
    }
}
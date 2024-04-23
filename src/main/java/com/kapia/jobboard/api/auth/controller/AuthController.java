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

@Tag(name = "Authentication", description = "Authentication operations")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private UserService authService;

    @Autowired
    AuthController(UserService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public Map<String, Object> registerUser(@RequestBody RegistrationRequest appUser) {
        return authService.registerUser(appUser);
    }

    @Operation(summary = "Login as a user")
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get user details")
    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppUser getUserDetails() {
        return authService.getUserDetails();
    }


}

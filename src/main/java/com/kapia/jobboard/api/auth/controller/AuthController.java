package com.kapia.jobboard.api.auth.controller;

import com.kapia.jobboard.api.auth.model.AppUser;
import com.kapia.jobboard.api.auth.request.LoginRequest;
import com.kapia.jobboard.api.auth.request.RegistrationRequest;
import com.kapia.jobboard.api.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService authService;

    @Autowired
    AuthController(UserService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public Map<String, Object> registerUser(@RequestBody RegistrationRequest appUser) {
        return authService.registerUser(appUser);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/user")
    public AppUser getUserDetails() {
        return authService.getUserDetails();
    }


}

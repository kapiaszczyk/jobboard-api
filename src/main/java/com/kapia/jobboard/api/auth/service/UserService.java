package com.kapia.jobboard.api.auth.service;

import com.kapia.jobboard.api.auth.enums.RoleEnum;
import com.kapia.jobboard.api.auth.exception.RoleNotFoundException;
import com.kapia.jobboard.api.auth.exception.UserAlreadyExistsException;
import com.kapia.jobboard.api.auth.exception.UserNotFoundException;
import com.kapia.jobboard.api.auth.model.AppUser;
import com.kapia.jobboard.api.auth.model.Role;
import com.kapia.jobboard.api.auth.repository.RoleRepository;
import com.kapia.jobboard.api.auth.repository.UserRepository;
import com.kapia.jobboard.api.auth.request.LoginRequest;
import com.kapia.jobboard.api.auth.request.RegistrationRequest;
import com.kapia.jobboard.api.auth.util.JWTUtil;
import com.kapia.jobboard.api.data.constants.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Service
public class UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JWTUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTUtil jwtUtil, AuthenticationManager authManager, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
        this.roleRepository = roleRepository;
    }

    public Map<String, Object> registerUser(RegistrationRequest dto) {

        String username = dto.getUsername();
        String password = dto.getPassword();
        String email = dto.getEmail();
        String role = dto.getRole();

        userRepository.findByEmail(email).ifPresent(user -> {
            throw new UserAlreadyExistsException(Messages.USER_ALREADY_EXISTS);
        });

        AppUser appUser = new AppUser();

        appUser.setUsername(username);
        appUser.setPassword(passwordEncoder.encode(password));
        appUser.setEmail(email);

        Role userRole = roleRepository.findByName(RoleEnum.valueOf(role))
                .orElseThrow(() -> new RoleNotFoundException(Messages.RESOURCE_NOT_FOUND)
                );

        appUser.setRole(userRole);

        userRepository.save(appUser);

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(appUser.getRole().getName().name()));

        String token = jwtUtil.generateToken(
                new UsernamePasswordAuthenticationToken(
                        appUser.getUsername(),
                        appUser.getPassword(),
                        authorities
                ));

        LOGGER.info("User registered: {} ", appUser.getUsername());

        return Map.of("jwt-token", token);
    }


    public Map<String, Object> login(LoginRequest loginRequest) {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        AppUser appUser = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(Messages.USER_NOT_FOUND));

        String username = appUser.getUsername();

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(appUser.getRole().getName().name()));

        try {

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    username,
                    password,
                    authorities
            );

            authManager.authenticate(authenticationToken);

            String token = jwtUtil.generateToken(authenticationToken);

            LOGGER.info("User logged in: {}", appUser.getUsername());

            return Map.of("jwt-token", token);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException(Messages.INVALID_DATA);
        }
    }

    public AppUser getUserDetails() {
        String email = (String)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(Messages.USER_NOT_FOUND));
    }

}


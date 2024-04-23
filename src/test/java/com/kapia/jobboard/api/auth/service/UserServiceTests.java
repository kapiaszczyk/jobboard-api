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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private JWTUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    @Test
    public void givenRegisterUser_whenRegisterUser_thenTokenIsReturned() {
        RegistrationRequest dto = new RegistrationRequest();
        dto.setUsername("test");
        dto.setPassword("password");
        dto.setEmail("test@test.com");
        dto.setRole("USER");

        Role role = new Role();
        role.setName(RoleEnum.USER);

        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
        when(roleRepository.findByName(any(RoleEnum.class))).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(jwtUtil.generateToken(any(UsernamePasswordAuthenticationToken.class))).thenReturn("token");

        Map<String, Object> result = userService.registerUser(dto);

        assertEquals("token", result.get("jwt-token"));
    }

    @Test
    public void givenRegisterUser_whenRegisterUserWithExistingEmail_thenUserAlreadyExistsExceptionIsThrown() {
        RegistrationRequest dto = new RegistrationRequest();
        dto.setUsername("test");
        dto.setPassword("password");
        dto.setEmail("test@test.com");
        dto.setRole("USER");

        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(new AppUser()));

        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(dto));
    }

    @Test
    public void givenRegisterUser_whenRegisterUserWithInvalidRole_thenRoleNotFoundExceptionIsThrown() {
        RegistrationRequest dto = new RegistrationRequest();
        dto.setUsername("test");
        dto.setPassword("password");
        dto.setEmail("test@test.com");
        dto.setRole("INVALID_ROLE");

        assertThrows(RoleNotFoundException.class, () -> userService.registerUser(dto));
    }

    @Test
    public void givenLogin_whenLogin_thenTokenIsReturned() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@test.com");
        loginRequest.setPassword("password");

        AppUser appUser = new AppUser();
        appUser.setUsername("test");
        Role role = new Role();
        role.setName(RoleEnum.USER);
        appUser.setRole(role);

        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(appUser));
        when(jwtUtil.generateToken(any(UsernamePasswordAuthenticationToken.class))).thenReturn("token");

        Map<String, Object> result = userService.login(loginRequest);

        assertEquals("token", result.get("jwt-token"));
    }

    @Test
    public void givenLogin_whenLoginWithInvalidEmail_thenUserNotFoundExceptionIsThrown() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("invalid@test.com");
        loginRequest.setPassword("password");

        assertThrows(UserNotFoundException.class, () -> userService.login(loginRequest));
    }


}
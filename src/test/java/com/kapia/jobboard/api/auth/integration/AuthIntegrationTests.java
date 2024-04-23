package com.kapia.jobboard.api.auth.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapia.jobboard.api.auth.request.LoginRequest;
import com.kapia.jobboard.api.auth.request.RegistrationRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureMockMvc
public class AuthIntegrationTests {

    @Container
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("jobboard")
            .withUsername("api")
            .withPassword("apipassword")
            .withInitScript("init.sql");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> POSTGRESQL_CONTAINER.getJdbcUrl());
        registry.add("spring.datasource.username", () -> "api");
        registry.add("spring.datasource.password", () -> "apipassword");
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
    }

    @Test
    @Transactional
    public void givenRegisterUser_whenPostRequest_thenReturnUser() throws Exception {
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RegistrationRequest("username", "username@email.com", "test", "USER"))))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void givenRegisterAdmin_whenPostRequest_thenReturnAdmin() throws Exception {
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RegistrationRequest("admin", "admin@email.com", "test", "ADMIN"))))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void givenRegisterSuperAdmin_whenPostRequest_thenReturnSuperAdmin() throws Exception {
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RegistrationRequest("superadmin", "superadmin@email.com", "test", "SUPER_ADMIN"))))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void givenLogin_whenPostRequest_thenReturnUser() throws Exception {
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RegistrationRequest("admin", "admin@email.com", "test", "ADMIN"))))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoginRequest("admin@email.com", "test"))))
                .andExpect(status().isOk());

    }

}

package com.kapia.jobboard.api.data.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapia.jobboard.api.data.dto.TechnologyDTO;
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

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureMockMvc(addFilters = false)
public class TechnologiesIntegrationTests {

    @Container
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("jobboard")
            .withUsername("api")
            .withPassword("apipassword");
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

    private TechnologyDTO createTestTechnologyDTO() {
        TechnologyDTO technologyDTO = new TechnologyDTO();
        technologyDTO.setDescription("Test description to pass the limit of the characters for the description field");
        technologyDTO.setName("Test Technology");
        technologyDTO.setLogo(null);
        return technologyDTO;
    }

    @Test
    @Transactional
    public void givenFindAllTechnologies_whenGetRequest_thenReturnAllTechnologies() throws Exception {
        mockMvc.perform(get("/api/v1/technologies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void givenAddTechnology_whenPostRequest_thenReturnCreated() throws Exception {
        mockMvc.perform(post("/api/v1/technologies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Collections.singletonList(createTestTechnologyDTO()))))
                .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    public void givenUpdateTechnology_whenPutRequest_thenReturnOk() throws Exception {
        String response = mockMvc.perform(post("/api/v1/technologies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Collections.singletonList(createTestTechnologyDTO()))))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        JsonNode jsonResponse = objectMapper.readTree(response);
        long technologyID = jsonResponse.get(0).get("id").asLong();

        mockMvc.perform(put("/api/v1/technologies/{id}", technologyID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTestTechnologyDTO())))
                .andExpect(status().isOk());
    }

}

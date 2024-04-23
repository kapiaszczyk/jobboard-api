package com.kapia.jobboard.api.data.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapia.jobboard.api.data.dto.CompanyAddressDTO;
import com.kapia.jobboard.api.data.model.Address;
import com.kapia.jobboard.api.data.model.Company;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureMockMvc(addFilters = false)
@Rollback(value = true)
public class CompaniesIntegrationTests {

    @Container
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("jobboard")
            .withUsername("api")
            .withPassword("apipassword");
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> POSTGRESQL_CONTAINER.getJdbcUrl());
        registry.add("spring.datasource.username", () -> "api");
        registry.add("spring.datasource.password", () -> "apipassword");
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
    }

    private CompanyAddressDTO createTestCompanyAddressDTO() {
        CompanyAddressDTO companyAddressDTO = new CompanyAddressDTO();
        Company company = new Company();
        company.setName("Test Company");
        company.setDescription("Test description that definitely has over 50 characters");
        company.setEmail("test@email.com");
        company.setWebsite("http://www.test.com");

        Address address = new Address();
        address.setStreet("Test Street");
        address.setCity("Test City");
        address.setPostalCode("12345");
        address.setCountry("Test Country");

        Set<Address> addresses = new HashSet<>();

        addresses.add(address);
        companyAddressDTO.setCompany(company);
        companyAddressDTO.setAddresses(addresses);

        return companyAddressDTO;
    }


    @Test
    @Transactional
    public void givenFindAll_WhenGetRequest_ThenReturnAllCompanies() throws Exception {
        mockMvc.perform(get("/api/v1/companies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void givenFindAllByCompanyName_WhenGetRequest_ThenReturnAllCompaniesByCompanyName() throws Exception {
        mockMvc.perform(post("/api/v1/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTestCompanyAddressDTO())))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/companies?name=Test Company")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void givenCompanyAddressDTO_whenAddCompany_thenReturnCreated() throws Exception {

        mockMvc.perform(post("/api/v1/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTestCompanyAddressDTO())))
                .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    public void givenCompanyId_whenDeleteCompany_thenReturnOk() throws Exception {
        String response = mockMvc.perform(post("/api/v1/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTestCompanyAddressDTO())))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        JsonNode jsonResponse = objectMapper.readTree(response);

        long companyId = jsonResponse.get("id").asLong();

        mockMvc.perform(delete("/api/v1/companies/{companyId}", companyId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void givenCompanyAddressDTO_whenUpdateCompany_thenReturnOk() throws Exception {
        String response = mockMvc.perform(post("/api/v1/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTestCompanyAddressDTO())))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        JsonNode jsonResponse = objectMapper.readTree(response);

        long companyId = jsonResponse.get("id").asLong();

        mockMvc.perform(put("/api/v1/companies/{companyId}", companyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTestCompanyAddressDTO())))
                .andExpect(status().isOk());
    }

}

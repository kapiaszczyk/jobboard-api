package com.kapia.jobboard.api.data.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapia.jobboard.api.data.constants.ContractType;
import com.kapia.jobboard.api.data.constants.Experience;
import com.kapia.jobboard.api.data.constants.OperatingMode;
import com.kapia.jobboard.api.data.constants.SalaryType;
import com.kapia.jobboard.api.data.dto.CompanyAddressDTO;
import com.kapia.jobboard.api.data.dto.JobOfferDTO;
import com.kapia.jobboard.api.data.model.Address;
import com.kapia.jobboard.api.data.model.Company;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
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

import java.time.Instant;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureMockMvc(addFilters = false)
public class JobOffersIntegrationTests {

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

    private JobOfferDTO createTestJobOfferDTO() throws Exception {

        long companyId = insertCompanyAndReturnId();

        JobOfferDTO jobOfferDTO = new JobOfferDTO();
        jobOfferDTO.setName("Test Job Offer");
        jobOfferDTO.setShortDescription("Test short description, short description, short description, short description");
        jobOfferDTO.setDescription("Test description over 50 characters, description over 50 characters, " +
                "description over 50 characters, description over 50 characters, long description over 50 characters" +
                "description over 50 characters, description over 50 characters, description over 50 characters, " +
                "description over 50 characters, description over 50 characters, description over 50 characters)");
        jobOfferDTO.setContractType(ContractType.FULL_TIME);
        jobOfferDTO.setSalary(1000);
        jobOfferDTO.setSalaryCurrency("USD");
        jobOfferDTO.setSalaryType(SalaryType.MONTHLY);
        jobOfferDTO.setExperience(Experience.JUNIOR);
        jobOfferDTO.setOperatingMode(OperatingMode.REMOTE);
        jobOfferDTO.setExpiresAt(Date.from(Instant.now().plusSeconds(60 * 60 * 24 * 30)));
        jobOfferDTO.setCompanyId(companyId);
        jobOfferDTO.setAddressId(companyId);

        Map<Long, String> technologies = new HashMap<>();
        technologies.put(1L, "Java");
        technologies.put(2L, "Spring Boot");
        jobOfferDTO.setTechnologies(technologies);

        return jobOfferDTO;
    }

    public long insertCompanyAndReturnId() throws Exception {
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

        String response = mockMvc.perform(post("/api/v1/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyAddressDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        JsonNode jsonResponse = objectMapper.readTree(response);

        return jsonResponse.get("id").asLong();
    }

    private void postJobOffer() throws Exception {
        JobOfferDTO jobOfferDTO = createTestJobOfferDTO();

        mockMvc.perform(post("/api/v1/job-offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobOfferDTO)))
                .andExpect(status().isCreated());

    }

    @Test
    @Transactional
    public void givenFindAllJobOffers_whenGetRequest_thenReturnAllJobOffers() throws Exception {
        mockMvc.perform(get("/api/v1/job-offers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @Disabled
    public void givenFindJobOfferById_whenGetRequest_thenReturnJobOffer() throws Exception {
        postJobOffer();

        mockMvc.perform(get("/api/v1/job-offers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @Disabled
    public void givenDeleteJobOffer_whenDeleteRequest_thenReturnOk() throws Exception {
        postJobOffer();

        mockMvc.perform(delete("/api/v1/job-offers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void givenAddJobOffer_whenPostRequest_thenReturnCreated() throws Exception {
        JobOfferDTO jobOfferDTO = createTestJobOfferDTO();

        mockMvc.perform(post("/api/v1/job-offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobOfferDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    @Disabled
    public void givenUpdateJobOffer_whenPutRequest_thenReturnOk() throws Exception {
        postJobOffer();

        JobOfferDTO jobOfferDTO = createTestJobOfferDTO();

        mockMvc.perform(put("/api/v1/job-offers/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobOfferDTO)))
                .andExpect(status().isOk());
    }

}

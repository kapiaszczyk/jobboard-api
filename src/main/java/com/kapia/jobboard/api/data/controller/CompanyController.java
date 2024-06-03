package com.kapia.jobboard.api.data.controller;

import com.kapia.jobboard.api.data.dto.CompanyAddressDTO;
import com.kapia.jobboard.api.data.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Company operations", description = "Retrieve and add company information")
@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

    private final CompanyService companyService;

    /**
     * Constructor for the CompanyController.
     *
     * @param companyService The service to be used for company related operations.
     */
    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * This method is responsible for retrieving all companies.
     *
     * @return A response entity with all companies.
     */
    @Operation(summary = "Get all companies")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllCompanies() {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.findAll());
    }

    /**
     * This method is responsible for retrieving a company by its name.
     *
     * @param name The name of the company to retrieve.
     * @return A response entity with the company.
     */
    @Operation(summary = "Get company by id")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = "name")
    public ResponseEntity<?> getCompanyByName(String name) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.findByName(name));
    }

    /**
     * This method is responsible for adding a new company.
     *
     * @param companyAddressDTO The DTO containing the company details.
     * @return A response entity with the added company.
     */
    @Operation(summary = "Add a new company")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addCompany(@RequestBody CompanyAddressDTO companyAddressDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.add(companyAddressDTO));
    }

    /**
     * This method is responsible for deleting a company.
     *
     * @param id The ID of the company to delete.
     * @return A response entity with the status of the operation.
     */
    @Operation(summary = "Delete a company")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCompany(@PathVariable(value = "id") long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * This method is responsible for updating a company.
     *
     * @param id The ID of the company to update.
     * @param dto The DTO containing the company details.
     * @return A response entity with the updated company.
     */
    @Operation(summary = "Update a company")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCompany(@PathVariable(value = "id") long id, @RequestBody CompanyAddressDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.update(dto, id));
    }

}

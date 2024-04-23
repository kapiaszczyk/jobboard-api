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

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Operation(summary = "Get all companies")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllCompanies() {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.findAll());
    }

    @Operation(summary = "Get company by id")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = "name")
    public ResponseEntity<?> getCompanyByName(String name) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.findByName(name));
    }

    @Operation(summary = "Add a new company")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addCompany(@RequestBody CompanyAddressDTO companyAddressDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.add(companyAddressDTO));
    }

    @Operation(summary = "Delete a company")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCompany(@PathVariable(value = "id") long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Update a company")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCompany(@PathVariable(value = "id") long id, @RequestBody CompanyAddressDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.update(dto, id));
    }

}

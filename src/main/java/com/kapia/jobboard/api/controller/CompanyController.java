package com.kapia.jobboard.api.controller;

import com.kapia.jobboard.api.dto.CompanyAddressDTO;
import com.kapia.jobboard.api.model.Address;
import com.kapia.jobboard.api.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCompanies() {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.findAll());
    }

    @GetMapping("/name")
    public ResponseEntity<?> getCompanyByName(String name) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<?> addCompany(@RequestBody CompanyAddressDTO companyAddressDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.add(companyAddressDTO));
    }

    @PostMapping("{id}/addresses")
    public ResponseEntity<?> updateAddresses(@RequestBody Set<Address> addresses, @PathVariable(name = "id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.updateAddresses(addresses, id));
    }

}

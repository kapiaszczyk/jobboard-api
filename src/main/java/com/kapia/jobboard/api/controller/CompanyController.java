package com.kapia.jobboard.api.controller;

import com.kapia.jobboard.api.dto.CompanyAddressDTO;
import com.kapia.jobboard.api.dto.CompanyUpdateDTO;
import com.kapia.jobboard.api.model.Address;
import com.kapia.jobboard.api.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllCompanies() {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.findAll());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = "name")
    public ResponseEntity<?> getCompanyByName(String name) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.findByName(name));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addCompany(@RequestBody CompanyAddressDTO companyAddressDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.add(companyAddressDTO));
    }

    @PostMapping(value = "/{id}/addresses", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAddresses(@RequestBody Set<Address> addresses, @PathVariable(name = "id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.updateAddresses(addresses, id));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCompany(@PathVariable(value = "id") long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCompany(@PathVariable(value = "id") long id, @RequestBody CompanyUpdateDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.update(dto, id));
    }

}

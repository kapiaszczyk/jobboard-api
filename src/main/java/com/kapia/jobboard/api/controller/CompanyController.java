package com.kapia.jobboard.api.controller;

import com.kapia.jobboard.api.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

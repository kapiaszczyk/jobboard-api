package com.kapia.jobboard.api.data.controller;

import com.kapia.jobboard.api.data.model.Address;
import com.kapia.jobboard.api.data.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Address operations", description = "Retrieve address information")
@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @Operation(summary = "Get all addresses")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Address> findAll() {
        return addressService.findAll();
    }


    @Operation(summary = "Get all addresses by company name")
    @GetMapping(value = "/companies/{companyName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Address> findAllByCompanyName(@PathVariable String companyName) {
        return addressService.findAllByCompanyName(companyName);
    }

}

package com.kapia.jobboard.api.controller;

import com.kapia.jobboard.api.model.Address;
import com.kapia.jobboard.api.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public List<Address> findAll() {
        return addressService.findAll();
    }

    @GetMapping("/{companyName}")
    public List<Address> findAllByCompanyName(@PathVariable String companyName) {
        return addressService.findAllByCompanyName(companyName);
    }

}

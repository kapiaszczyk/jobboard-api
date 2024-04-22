package com.kapia.jobboard.api.data.service;

import com.kapia.jobboard.api.data.model.Address;
import com.kapia.jobboard.api.data.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public List<Address> findAllByCompanyName(String companyName) {
        return addressRepository.findAllByCompanyName(companyName);
    }
}

package com.kapia.jobboard.api.data.service;

import com.kapia.jobboard.api.data.model.Address;
import com.kapia.jobboard.api.data.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class represents the service layer for managing addresses.
 */
@Service
public class AddressService {

    private final AddressRepository addressRepository;

    /**
     * Constructor for the AddressService.
     *
     * @param addressRepository The repository to be used for address related operations.
     */
    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    /**
     * This method is responsible for retrieving all addresses.
     *
     * @return A list of all addresses.
     */
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    /**
     * This method is responsible for retrieving all addresses by company name.
     *
     * @param companyName The name of the company to retrieve addresses for.
     * @return A list of addresses for the specified company.
     */
    public List<Address> findAllByCompanyName(String companyName) {
        return addressRepository.findAllByCompanyName(companyName);
    }
}

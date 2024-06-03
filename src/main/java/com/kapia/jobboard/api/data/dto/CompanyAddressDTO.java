package com.kapia.jobboard.api.data.dto;

import com.kapia.jobboard.api.data.model.Address;
import com.kapia.jobboard.api.data.model.Company;

import java.util.Set;

/**
 * This class represents a DTO for company and address data.
 */
public class CompanyAddressDTO {

    Company company;

    Set<Address> addresses;

    /**
     * Constructor for the CompanyAddressDTO.
     */
    public CompanyAddressDTO() {
    }

    /**
     * Get the company.
     *
     * @return The company.
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Set the company.
     *
     * @param company The company.
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * Get the addresses.
     *
     * @return The addresses.
     */
    public Set<Address> getAddresses() {
        return addresses;
    }

    /**
     * Set the addresses.
     *
     * @param addresses The addresses.
     */
    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    /**
     * This method is responsible for returning a string representation of the CompanyAddressDTO.
     * @return A string representation of the CompanyAddressDTO.
     */
    @Override
    public String toString() {
        return "CompanyAddressDTO{" +
                "company=" + company +
                ", addresses=" + addresses +
                '}';
    }
}

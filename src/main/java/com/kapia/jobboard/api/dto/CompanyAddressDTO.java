package com.kapia.jobboard.api.dto;

import com.kapia.jobboard.api.model.Address;
import com.kapia.jobboard.api.model.Company;

import java.util.Set;

public class CompanyAddressDTO {

    Company company;

    Set<Address> addresses;

    public CompanyAddressDTO() {
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "CompanyAddressDTO{" +
                "company=" + company +
                ", addresses=" + addresses +
                '}';
    }
}

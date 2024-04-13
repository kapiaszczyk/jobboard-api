package com.kapia.jobboard.api.service;

import com.kapia.jobboard.api.dto.CompanyAddressDTO;
import com.kapia.jobboard.api.model.Address;
import com.kapia.jobboard.api.model.Company;
import com.kapia.jobboard.api.repository.CompanyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Optional<Company> findByName(String name) {
        return companyRepository.findByName(name);
    }

    public Company add(CompanyAddressDTO companyAddressDTO) {
        Company company = companyAddressDTO.getCompany();
        Set<Address> addressSet = companyAddressDTO.getAddresses();
        if (company == null || addressSet.isEmpty()) throw new RuntimeException("Incomplete data");

        company.addAddresses(addressSet);

        return companyRepository.save(company);

    }

    @Transactional
    public Company updateAddresses(Set<Address> addresses, long id) {
        Company companyToUpdate = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));

        companyToUpdate.addAddresses(addresses);

        companyRepository.save(companyToUpdate);

        return companyToUpdate;
    }
}

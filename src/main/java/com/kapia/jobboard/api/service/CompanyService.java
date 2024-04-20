package com.kapia.jobboard.api.service;

import com.kapia.jobboard.api.constants.Messages;
import com.kapia.jobboard.api.dto.CompanyAddressDTO;
import com.kapia.jobboard.api.dto.CompanyUpdateDTO;
import com.kapia.jobboard.api.exception.ResourceNotFoundException;
import com.kapia.jobboard.api.mapper.CompanyMappper;
import com.kapia.jobboard.api.model.Address;
import com.kapia.jobboard.api.model.Company;
import com.kapia.jobboard.api.repository.CompanyRepository;
import com.kapia.jobboard.api.repository.JobOfferRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    private final JobOfferRepository jobOfferRepository;

    private final CompanyMappper companyMappper;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, JobOfferRepository jobOfferRepository, CompanyMappper companyMappper) {
        this.companyRepository = companyRepository;
        this.jobOfferRepository = jobOfferRepository;
        this.companyMappper = companyMappper;
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
        Company companyToUpdate = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND));

        companyToUpdate.addAddresses(addresses);

        companyRepository.save(companyToUpdate);

        return companyToUpdate;
    }

    public void deleteCompany(long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND));
        jobOfferRepository.deleteJobOfferTechnologiesByCompanyId(id);
        jobOfferRepository.deleteByCompanyId(id);
        companyRepository.delete(company);
    }

    @Transactional
    public Company update(CompanyUpdateDTO dto, long id) {
        Company companyToUpdate = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND));
        companyToUpdate = companyMappper.updateCompanyFromDto(dto, companyToUpdate);
        return companyRepository.save(companyToUpdate);
    }

}

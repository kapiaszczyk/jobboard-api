package com.kapia.jobboard.api.service;

import com.kapia.jobboard.api.constants.Messages;
import com.kapia.jobboard.api.dto.CompanyAddressDTO;
import com.kapia.jobboard.api.exception.IncompleteDataException;
import com.kapia.jobboard.api.exception.ResourceNotFoundException;
import com.kapia.jobboard.api.mapper.CompanyMapper;
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

    private final CompanyMapper companyMapper;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, JobOfferRepository jobOfferRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.jobOfferRepository = jobOfferRepository;
        this.companyMapper = companyMapper;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Optional<Company> findByName(String name) {
        return companyRepository.findByName(name);
    }

    @Transactional
    public Company add(CompanyAddressDTO companyAddressDTO) {
        Company company = companyAddressDTO.getCompany();
        Set<Address> addressSet = companyAddressDTO.getAddresses();

        if (company == null || addressSet == null || addressSet.isEmpty())
            throw new IncompleteDataException(Messages.INCOMPLETE_DATA);

        company.addAddresses(addressSet);
        return companyRepository.save(company);

    }

    @Transactional
    public Company update(CompanyAddressDTO dto, long id) {
        if (dto == null)
            throw new IncompleteDataException(Messages.INCOMPLETE_DATA);

        Company companyToUpdate = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND));

        return companyRepository.save(companyMapper.updateCompanyFromDto(dto, companyToUpdate));
    }

    public void deleteCompany(long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND));
        jobOfferRepository.deleteJobOfferTechnologiesByCompanyId(id);
        jobOfferRepository.deleteByCompanyId(id);
        companyRepository.delete(company);
    }

}

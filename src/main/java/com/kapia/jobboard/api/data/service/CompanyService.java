package com.kapia.jobboard.api.data.service;

import com.kapia.jobboard.api.data.constants.Messages;
import com.kapia.jobboard.api.data.dto.CompanyAddressDTO;
import com.kapia.jobboard.api.data.exception.IncompleteDataException;
import com.kapia.jobboard.api.data.exception.ResourceNotFoundException;
import com.kapia.jobboard.api.data.mapper.CompanyMapper;
import com.kapia.jobboard.api.data.model.Address;
import com.kapia.jobboard.api.data.model.Company;
import com.kapia.jobboard.api.data.repository.CompanyRepository;
import com.kapia.jobboard.api.data.repository.JobOfferRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The CompanyService class provides methods for managing company data.
 */
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    private final JobOfferRepository jobOfferRepository;

    private final CompanyMapper companyMapper;

    /**
     * Constructor for the CompanyService.
     *
     * @param companyRepository The repository to be used for company related operations.
     * @param jobOfferRepository The repository to be used for job offer related operations.
     * @param companyMapper The mapper to be used for company related operations.
     */
    @Autowired
    public CompanyService(CompanyRepository companyRepository, JobOfferRepository jobOfferRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.jobOfferRepository = jobOfferRepository;
        this.companyMapper = companyMapper;
    }

    /**
     * This method is responsible for retrieving all companies.
     *
     * @return A list of all companies.
     */
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    /**
     * This method is responsible for retrieving a company by ID.
     *
     * @param name The name of the company to retrieve.
     * @return The company with the specified ID.
     */
    public Optional<Company> findByName(String name) {
        return companyRepository.findByName(name);
    }

    /**
     * This method is responsible for adding a new company.
     *
     * @param companyAddressDTO The company and addresses to add.
     * @return The added company.
     */
    @Transactional
    public Company add(CompanyAddressDTO companyAddressDTO) {
        Company company = companyAddressDTO.getCompany();
        Set<Address> addressSet = companyAddressDTO.getAddresses();

        if (company == null || addressSet == null || addressSet.isEmpty())
            throw new IncompleteDataException(Messages.INCOMPLETE_DATA);

        company.addAddresses(addressSet);
        return companyRepository.save(company);

    }

    /**
     * This method is responsible for updating a company.
     *
     * @param dto The company and addresses to update.
     * @param id The ID of the company to update.
     * @return The updated company.
     */
    @Transactional
    public Company update(CompanyAddressDTO dto, long id) {
        if (dto == null)
            throw new IncompleteDataException(Messages.INCOMPLETE_DATA);

        Company companyToUpdate = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND));

        return companyRepository.save(companyMapper.updateCompanyFromDto(dto, companyToUpdate));
    }

    /**
     * This method is responsible for deleting a company.
     *
     * @param id The ID of the company to delete.
     */
    public void deleteCompany(long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND));
        jobOfferRepository.deleteJobOfferTechnologiesByCompanyId(id);
        jobOfferRepository.deleteByCompanyId(id);
        companyRepository.delete(company);
    }

}

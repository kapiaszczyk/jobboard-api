package com.kapia.jobboard.api.service;

import com.kapia.jobboard.api.model.*;
import com.kapia.jobboard.api.payload.JobOfferRequest;
import com.kapia.jobboard.api.projections.JobOfferProjection;
import com.kapia.jobboard.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JobOfferService {

    private final JobOfferRepository jobOfferRepository;

    private final CompanyRepository companyRepository;

    private final AddressRepository addressRepository;

    private final TechnologyRepository technologyRepository;

    private final JobOfferTechnologyRepository jobOfferTechnologyRepository;

    @Autowired
    public JobOfferService(JobOfferRepository jobOfferRepository, CompanyRepository companyRepository,
                           AddressRepository addressRepository, TechnologyRepository technologyRepository,
                           JobOfferTechnologyRepository jobOfferTechnologyRepository) {
        this.jobOfferRepository = jobOfferRepository;
        this.companyRepository = companyRepository;
        this.addressRepository = addressRepository;
        this.technologyRepository = technologyRepository;
        this.jobOfferTechnologyRepository = jobOfferTechnologyRepository;
    }

    public Optional<JobOffer> findJobOfferById(Long id) {
        return jobOfferRepository.findById(id);
    }

    public List<JobOffer> findAllJobOffers() {
        return jobOfferRepository.findAll();
    }

    public List<JobOfferProjection> findAllProjectedBy() {
        return jobOfferRepository.findAllProjectedBy();
    }

    public List<JobOffer> findJobOfferByName(String name) {
        return jobOfferRepository.findJobOfferByName(name);
    }

    /*
     * Optimisation candidate (generates too many queries to the DB!)
     */
    public Optional<JobOfferProjection> add(JobOfferRequest jobOfferRequest) {

        Company company = companyRepository.findById(jobOfferRequest.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Address address = addressRepository.findById(jobOfferRequest.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        JobOffer jobOffer = new JobOffer(
                jobOfferRequest.getName(), jobOfferRequest.getShortDescription(), jobOfferRequest.getDescription(),
                jobOfferRequest.getContractType(), jobOfferRequest.getSalary(), jobOfferRequest.getSalaryCurrency(),
                jobOfferRequest.getSalaryType(), jobOfferRequest.getExperience(), jobOfferRequest.getOperatingMode(),
                jobOfferRequest.getExpiresAt(), address, company);

        jobOffer = jobOfferRepository.save(jobOffer);

        Set<JobOfferTechnology> jobOfferTechnologies = new HashSet<>();

        List<Technology> technologies = technologyRepository.findAllById(jobOfferRequest.getTechnologies().keySet());

        for (Technology technology : technologies) {
            Long technologyId = technology.getId();
            String degreeOfKnowledge = jobOfferRequest.getTechnologies().get(technologyId);

            jobOfferTechnologies.add(new JobOfferTechnology(
                    new JobOfferTechnologyKey(jobOffer.getId(), technologyId),
                    jobOffer,
                    technology,
                    degreeOfKnowledge
            ));
        }

        jobOfferTechnologyRepository.saveAll(jobOfferTechnologies);
        jobOffer.setTechnologies(jobOfferTechnologies);
        jobOfferRepository.save(jobOffer);

        return jobOfferRepository.findProjectedById(jobOffer.getId());
    }

}

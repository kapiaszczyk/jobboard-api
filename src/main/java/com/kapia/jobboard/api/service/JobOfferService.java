package com.kapia.jobboard.api.service;

import com.kapia.jobboard.api.model.JobOffer;
import com.kapia.jobboard.api.projections.JobOfferBasicView;
import com.kapia.jobboard.api.projections.JobOfferDetailedView;
import com.kapia.jobboard.api.repository.JobOfferRepository;
import com.kapia.jobboard.api.model.*;
import com.kapia.jobboard.api.payload.JobOfferRequest;
import com.kapia.jobboard.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<JobOfferBasicView> findAllProjectedBy() {
        return jobOfferRepository.findAllProjectedBy();
    }

    public List<JobOffer> findJobOfferByName(String name) {
        return jobOfferRepository.findJobOfferByName(name);
    }

    /*
     * Optimisation candidate (generates too many queries to the DB!)
     */
    public Optional<JobOfferBasicView> add(JobOfferRequest jobOfferRequest) {

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

    /*
     * Optimisation candidate (generates too many queries to the DB!)
     */
    public JobOffer update(JobOfferRequest jobOfferRequest, Long id) {
        JobOffer jobOfferToUpdate = jobOfferRepository.findById(id).orElseThrow(() -> new RuntimeException("Invalid id"));

        jobOfferToUpdate.setName(jobOfferRequest.getName());
        jobOfferToUpdate.setShortDescription(jobOfferRequest.getShortDescription());
        jobOfferToUpdate.setDescription(jobOfferRequest.getDescription());
        jobOfferToUpdate.setContractType(jobOfferRequest.getContractType());
        jobOfferToUpdate.setSalary(jobOfferRequest.getSalary());
        jobOfferToUpdate.setSalaryCurrency(jobOfferRequest.getSalaryCurrency());
        jobOfferToUpdate.setSalaryType(jobOfferRequest.getSalaryType());
        jobOfferToUpdate.setExperience(jobOfferRequest.getExperience());
        jobOfferToUpdate.setOperatingMode(jobOfferRequest.getOperatingMode());
        jobOfferToUpdate.setExpiresAt(jobOfferRequest.getExpiresAt());

        if (!Objects.equals(jobOfferToUpdate.getAddress().getId(), jobOfferRequest.getAddressId())) {
            Address address = addressRepository.findById(jobOfferRequest.getAddressId())
                    .orElseThrow(() -> new RuntimeException("Address not found"));
            jobOfferToUpdate.setAddress(address);
        }

        List<Technology> technologies = technologyRepository.findAllById(jobOfferRequest.getTechnologies().keySet());

        Map<Long, Technology> technologyMap = technologies.stream()
                .collect(Collectors.toMap(Technology::getId, tech -> tech));

        Set<JobOfferTechnology> jobOfferTechnologies = jobOfferToUpdate.getTechnologies();

        for (Map.Entry<Long, String> entry : jobOfferRequest.getTechnologies().entrySet()) {
            Long technologyId = entry.getKey();
            String degreeOfKnowledge = entry.getValue();

            // Check if the job offer already has a technology with the given ID
            Optional<JobOfferTechnology> existingJobOfferTechnologyO = jobOfferTechnologies.stream()
                    .filter(jot -> jot.getTechnology().getId().equals(technologyId))
                    .findFirst();

            if (existingJobOfferTechnologyO.isPresent()) {
                JobOfferTechnology existingJobOfferTechnology = existingJobOfferTechnologyO.get();
                existingJobOfferTechnology.setDegreeOfKnowledge(degreeOfKnowledge);
            } else {
                Technology technology = technologyMap.get(technologyId);
                JobOfferTechnology newJobOfferTechnology = new JobOfferTechnology(jobOfferToUpdate, technology, degreeOfKnowledge);
                jobOfferTechnologies.add(newJobOfferTechnology);
            }
        }

        jobOfferTechnologies.removeIf(jot -> !jobOfferRequest.getTechnologies().containsKey(jot.getTechnology().getId()));

        return jobOfferRepository.save(jobOfferToUpdate);

    }

    public void deleteById(long id) {
        jobOfferRepository.deleteById(id);
    }

}

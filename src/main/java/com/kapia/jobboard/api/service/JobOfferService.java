package com.kapia.jobboard.api.service;

import com.kapia.jobboard.api.constants.DegreeOfKnowledge;
import com.kapia.jobboard.api.constants.SortingCriteria;
import com.kapia.jobboard.api.constants.SortingOrder;
import com.kapia.jobboard.api.model.*;
import com.kapia.jobboard.api.payload.JobOfferRequest;
import com.kapia.jobboard.api.projections.JobOfferBasicView;
import com.kapia.jobboard.api.projections.JobOfferDetailedView;
import com.kapia.jobboard.api.repository.*;
import com.kapia.jobboard.api.searchcriteria.JobOfferSearchCriteria;
import com.kapia.jobboard.api.specifications.JobOfferSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobOfferService {

    private static final int MAX_PAGE_SIZE = 25;

    private CacheManager cacheManager;

    private final JobOfferRepository jobOfferRepository;

    private final CompanyRepository companyRepository;

    private final AddressRepository addressRepository;

    private final TechnologyRepository technologyRepository;

    private final JobOfferTechnologyRepository jobOfferTechnologyRepository;

    @Autowired
    public JobOfferService(JobOfferRepository jobOfferRepository, CompanyRepository companyRepository,
                           AddressRepository addressRepository, TechnologyRepository technologyRepository,
                           JobOfferTechnologyRepository jobOfferTechnologyRepository, CacheManager cacheManager) {
        this.jobOfferRepository = jobOfferRepository;
        this.companyRepository = companyRepository;
        this.addressRepository = addressRepository;
        this.technologyRepository = technologyRepository;
        this.jobOfferTechnologyRepository = jobOfferTechnologyRepository;
        this.cacheManager = cacheManager;
    }

    @Cacheable(cacheNames = "job_offers", key = "#id")
    public Optional<JobOffer> findJobOfferById(Long id) {
        return jobOfferRepository.findById(id);
    }

    public List<JobOffer> findAllJobOffers() {
        return jobOfferRepository.findAll();
    }

    @Cacheable(cacheNames = "job_offers_basic_view", key = "'findAllBasicProjectedBy'")
    public List<JobOfferBasicView> findAllBasicProjectedBy() {
        return jobOfferRepository.findAllBasicProjectedBy();
    }

    @Cacheable(cacheNames = "job_offers_basic_view", key = "#id")
    public Optional<JobOfferBasicView> findBasicProjectedById(Long id) {
        return jobOfferRepository.findBasicProjectedById(id);
    }

    @Cacheable(cacheNames = "job_offers_detailed_view", key = "'findAllDetailedProjectedBy'")
    public List<JobOfferDetailedView> findAllDetailedProjectedBy() {
        return jobOfferRepository.findAllDetailedProjectedBy();
    }

    @Cacheable(cacheNames = "job_offers_detailed_view", key = "#id")
    public Optional<JobOfferDetailedView> findDetailedProjectedById(Long id) {
        return jobOfferRepository.findDetailedProjectedById(id);
    }

    public List<JobOffer> findJobOfferByName(String name) {
        return jobOfferRepository.findJobOfferByName(name);
    }


    /*
     * Optimisation candidate (generates too many queries to the DB!)
     */
    @Transactional
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
            DegreeOfKnowledge degreeOfKnowledge = DegreeOfKnowledge.valueOf(jobOfferRequest.getTechnologies().get(technologyId));

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
    @Transactional
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
            DegreeOfKnowledge degreeOfKnowledge = DegreeOfKnowledge.valueOf(entry.getValue());

            // Check if the job offer already has a technology with the given ID
            Optional<JobOfferTechnology> existingJobOfferTechnologyO = jobOfferTechnologies.stream()
                    .filter(jot -> jot.getTechnology().getId().equals(technologyId))
                    .findFirst();

            if (existingJobOfferTechnologyO.isPresent()) {
                JobOfferTechnology existingJobOfferTechnology = existingJobOfferTechnologyO.get();
                existingJobOfferTechnology.setDegreeOfKnowledge(degreeOfKnowledge);
            } else {
                Technology technology = technologyMap.get(technologyId);
                JobOfferTechnology newJobOfferTechnology = new JobOfferTechnology(new JobOfferTechnologyKey(jobOfferToUpdate.getId(), technology.getId()), jobOfferToUpdate, technology, degreeOfKnowledge);
                jobOfferTechnologies.add(newJobOfferTechnology);
            }
        }

        jobOfferTechnologies.removeIf(jot -> !jobOfferRequest.getTechnologies().containsKey(jot.getTechnology().getId()));

        return jobOfferRepository.save(jobOfferToUpdate);

    }

    public void deleteById(long id) {
        jobOfferRepository.deleteById(id);
    }

    public List<JobOffer> findJobOfferByCriteria(JobOfferSearchCriteria jobOfferSearchCriteria) {
        Specification<JobOffer> specification = JobOfferSpecifications.createJobOfferSpecification(jobOfferSearchCriteria);
        return jobOfferRepository.findAll(specification);
    }

    public List<JobOfferDetailedView> getJobOfferByCriteriaProjectedBy(JobOfferSearchCriteria jobOfferSearchCriteria) {
        Specification<JobOffer> specification = JobOfferSpecifications.createJobOfferSpecification(jobOfferSearchCriteria);
        return jobOfferRepository.findBy(specification, q -> q
                .project("name", "company.name", "address.city", "technologies.technology.name", "operatingMode", "contractType", "experience", "salary", "salaryCurrency")
                .as(JobOfferDetailedView.class)
                .all()
        );
    }

    public List<JobOffer> findJobOfferByCriteriaPageAndSortByCreatedAtAsc(JobOfferSearchCriteria jobOfferSearchCriteria, int pageSize, int pageNumber, String sortingCriteria, String sortingOrder) {
        Specification<JobOffer> specification = JobOfferSpecifications.createJobOfferSpecification(jobOfferSearchCriteria);

        if (pageSize > MAX_PAGE_SIZE) pageSize = MAX_PAGE_SIZE;
        Sort sort = resolveSorting(sortingCriteria, sortingOrder);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        return jobOfferRepository.findAll(specification, pageable).stream().toList();
    }

    public List<JobOfferDetailedView> findJobOfferByCriteriaPageAndSortByCreatedAtAscProjectedBy(JobOfferSearchCriteria jobOfferSearchCriteria, int pageSize, int pageNumber) {
        Specification<JobOffer> specification = JobOfferSpecifications.createJobOfferSpecification(jobOfferSearchCriteria);

        if (pageSize > MAX_PAGE_SIZE) pageSize = MAX_PAGE_SIZE;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return jobOfferRepository.findBy(specification, q -> q
                .project("name", "company.name", "address.city", "technologies.technology.name", "operatingMode", "contractType", "experience", "salary", "salaryCurrency")
                .as(JobOfferDetailedView.class)
                .page(pageable)
        ).stream().toList();
    }

    private Sort resolveSorting(String sortBy, String sortDirection) {
        String field = resolveSortingCriteria(sortBy);
        String order = resolveSortingOrder(sortDirection);
        return Sort.by(Sort.Order.by(field).with(Sort.Direction.fromString(order)));
    }

    private String resolveSortingCriteria(String sortBy) {
        try {
            return SortingCriteria.fromString(sortBy.toUpperCase()).toString();
        } catch (IllegalArgumentException e) {
            return SortingCriteria.CREATED_AT.toString();
        }
    }

    private String resolveSortingOrder(String sortDirection) {
        try {
            return SortingOrder.fromString(sortDirection.toUpperCase()).toString();
        } catch (IllegalArgumentException e) {
            return SortingOrder.ASC.toString();
        }
    }

}

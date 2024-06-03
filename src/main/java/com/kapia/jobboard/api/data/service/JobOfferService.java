package com.kapia.jobboard.api.data.service;

import com.kapia.jobboard.api.data.constants.*;
import com.kapia.jobboard.api.data.dto.JobOfferDTO;
import com.kapia.jobboard.api.data.exception.ResourceNotFoundException;
import com.kapia.jobboard.api.data.model.*;
import com.kapia.jobboard.api.data.projections.JobOfferBasicView;
import com.kapia.jobboard.api.data.projections.JobOfferDetailedView;
import com.kapia.jobboard.api.data.repository.*;
import com.kapia.jobboard.api.data.searchcriteria.JobOfferSearchCriteria;
import com.kapia.jobboard.api.data.specifications.JobOfferSpecifications;
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

/**
 * The JobOfferService class is responsible for providing various operations related to job offers.
 * It interacts with the JobOfferRepository, CompanyRepository, AddressRepository, TechnologyRepository,
 * and JobOfferTechnologyRepository to perform CRUD operations and retrieve job offer data.
 * This class also utilizes caching to improve performance for certain operations.
 */
@Service
public class JobOfferService {

    private final CacheManager cacheManager;

    private final JobOfferRepository jobOfferRepository;

    private final CompanyRepository companyRepository;

    private final AddressRepository addressRepository;

    private final TechnologyRepository technologyRepository;

    private final JobOfferTechnologyRepository jobOfferTechnologyRepository;

    /**
     * Constructs a new JobOfferService with the given repositories and cache manager.
     *
     * @param jobOfferRepository the job offer repository
     * @param companyRepository the company repository
     * @param addressRepository the address repository
     * @param technologyRepository the technology repository
     * @param jobOfferTechnologyRepository the job offer technology repository
     * @param cacheManager the cache manager
     */
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

    /**
     * Method to find a job offer by its ID.
     *
     * @param id The ID of the job offer to find.
     * @return The job offer with the given ID.
     */
    @Cacheable(cacheNames = "job_offers", key = "#id")
    public Optional<JobOffer> findJobOfferById(Long id) {
        return jobOfferRepository.findById(id);
    }

    /**
     * Method to find all job offers.
     *
     * @return A list of all job offers.
     */
    public List<JobOffer> findAllJobOffers() {
        return jobOfferRepository.findAll();
    }

    /**
     * Method to find all job offers projected into a DTO.
     *
     * @return A list of all job offers projected into a DTO.
     */
    @Cacheable(cacheNames = "job_offers_basic_view", key = "'findAllBasicProjectedBy'")
    public List<JobOfferBasicView> findAllBasicProjectedBy() {
        return jobOfferRepository.findAllBasicProjectedBy();
    }

    /**
     * Method to find a job offer by its ID and project it into a DTO.
     *
     * @param id The ID of the job offer to find.
     * @return The job offer with the given ID projected into a DTO.
     */
    @Cacheable(cacheNames = "job_offers_basic_view", key = "#id")
    public Optional<JobOfferBasicView> findBasicProjectedById(Long id) {
        return jobOfferRepository.findBasicProjectedById(id);
    }

    /**
     * Method to find all job offers and project them into a DTO.
     *
     * @return A list of all job offers projected into a DTO.
     */
    @Cacheable(cacheNames = "job_offers_detailed_view", key = "'findAllDetailedProjectedBy'")
    public List<JobOfferDetailedView> findAllDetailedProjectedBy() {
        return jobOfferRepository.findAllDetailedProjectedBy();
    }

    /**
     * Method to find a job offer by its ID and project it into a DTO.
     *
     * @param id The ID of the job offer to find.
     * @return The job offer with the given ID projected into a DTO.
     */
    @Cacheable(cacheNames = "job_offers_detailed_view", key = "#id")
    public Optional<JobOfferDetailedView> findDetailedProjectedById(Long id) {
        return jobOfferRepository.findDetailedProjectedById(id);
    }

    /**
     * Method to add a new job offer to the database.
     * 
     * @param jobOfferDTO The DTO containing the job offer data.
     * @return The newly added job offer.
     * @throws ResourceNotFoundException If the company or address with the given ID does not exist.
     */
    @Transactional
    public Optional<JobOfferBasicView> add(JobOfferDTO jobOfferDTO) {

        Company company = companyRepository.findById(jobOfferDTO.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        Address address = addressRepository.findById(jobOfferDTO.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        JobOffer jobOffer = new JobOffer(
                jobOfferDTO.getName(), jobOfferDTO.getShortDescription(), jobOfferDTO.getDescription(),
                jobOfferDTO.getContractType(), jobOfferDTO.getSalary(), jobOfferDTO.getSalaryCurrency(),
                jobOfferDTO.getSalaryType(), jobOfferDTO.getExperience(), jobOfferDTO.getOperatingMode(),
                jobOfferDTO.getExpiresAt(), address, company);

        jobOffer = jobOfferRepository.save(jobOffer);

        Set<JobOfferTechnology> jobOfferTechnologies = new HashSet<>();

        List<Technology> technologies = technologyRepository.findAllById(jobOfferDTO.getTechnologies().keySet());

        for (Technology technology : technologies) {
            Long technologyId = technology.getId();
            DegreeOfKnowledge degreeOfKnowledge = DegreeOfKnowledge.valueOf(jobOfferDTO.getTechnologies().get(technologyId));

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


    /**
     * Method to update a job offer by its ID.
     * 
     * @param jobOfferDTO The DTO containing the updated job offer data.
     * @param id The ID of the job offer to update.
     * 
     * @return The updated job offer.
     * @throws ResourceNotFoundException If the job offer with the given ID does not exist.
     */
    @Transactional
    public JobOffer update(JobOfferDTO jobOfferDTO, Long id) {
        JobOffer jobOfferToUpdate = jobOfferRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND));

        jobOfferToUpdate.setName(jobOfferDTO.getName());
        jobOfferToUpdate.setShortDescription(jobOfferDTO.getShortDescription());
        jobOfferToUpdate.setDescription(jobOfferDTO.getDescription());
        jobOfferToUpdate.setContractType(jobOfferDTO.getContractType());
        jobOfferToUpdate.setSalary(jobOfferDTO.getSalary());
        jobOfferToUpdate.setSalaryCurrency(jobOfferDTO.getSalaryCurrency());
        jobOfferToUpdate.setSalaryType(jobOfferDTO.getSalaryType());
        jobOfferToUpdate.setExperience(jobOfferDTO.getExperience());
        jobOfferToUpdate.setOperatingMode(jobOfferDTO.getOperatingMode());
        jobOfferToUpdate.setExpiresAt(jobOfferDTO.getExpiresAt());

        if (!Objects.equals(jobOfferToUpdate.getAddress().getId(), jobOfferDTO.getAddressId())) {
            Address address = addressRepository.findById(jobOfferDTO.getAddressId())
                    .orElseThrow(() -> new RuntimeException("Address not found"));
            jobOfferToUpdate.setAddress(address);
        }

        List<Technology> technologies = technologyRepository.findAllById(jobOfferDTO.getTechnologies().keySet());

        Map<Long, Technology> technologyMap = technologies.stream()
                .collect(Collectors.toMap(Technology::getId, tech -> tech));

        Set<JobOfferTechnology> jobOfferTechnologies = jobOfferToUpdate.getTechnologies();

        for (Map.Entry<Long, String> entry : jobOfferDTO.getTechnologies().entrySet()) {
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

        jobOfferTechnologies.removeIf(jot -> !jobOfferDTO.getTechnologies().containsKey(jot.getTechnology().getId()));

        return jobOfferRepository.save(jobOfferToUpdate);

    }

    /**
     * Method to delete a job offer by its ID.
     * 
     * @param id The ID of the job offer to delete.
     * @throws ResourceNotFoundException If the job offer with the given ID does not exist.
     */
    public void deleteById(long id) {
        JobOffer jobOffer = jobOfferRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND));
        jobOfferRepository.delete(jobOffer);
    }

    /**
     * Method to delete all job offers.
     * 
     * @param jobOfferSearchCriteria The search criteria to filter job offers.
     * @return A list of job offers that match the search criteria.
     * @throws ResourceNotFoundException If the job offer with the given ID does not exist.
     */
    public List<JobOffer> findJobOfferByCriteria(JobOfferSearchCriteria jobOfferSearchCriteria) {
        Specification<JobOffer> specification = JobOfferSpecifications.createJobOfferSpecification(jobOfferSearchCriteria);
        return jobOfferRepository.findAll(specification);
    }

    /**
     * Method to retrieve job offers by search criteria and project them into a DTO.
     * 
     * @param jobOfferSearchCriteria The search criteria to filter job offers.
     * @return A list of job offers projected into a DTO.
     */
    public List<JobOfferDetailedView> getJobOfferByCriteriaProjectedBy(JobOfferSearchCriteria jobOfferSearchCriteria) {
        Specification<JobOffer> specification = JobOfferSpecifications.createJobOfferSpecification(jobOfferSearchCriteria);
        return jobOfferRepository.findBy(specification, q -> q
                .project("name", "company.name", "address.city", "technologies.technology.name", "operatingMode", "contractType", "experience", "salary", "salaryCurrency")
                .as(JobOfferDetailedView.class)
                .all()
        );
    }

    /**
     * Method to retrieve job offers by search criteria and sort them by created date in ascending order.
     * 
     * @param jobOfferSearchCriteria The search criteria to filter job offers.
     * @param pageSize The number of job offers to retrieve per page.
     * @param pageNumber The page number to retrieve job offers from.
     * @param sortingCriteria The criteria to sort job offers by.
     * @param sortingOrder The order to sort job offers by.
     * @return A list of job offers sorted by created date in ascending order.
     */
    public List<JobOffer> findJobOfferByCriteriaPageAndSortByCreatedAtAsc(JobOfferSearchCriteria jobOfferSearchCriteria, int pageSize, int pageNumber, String sortingCriteria, String sortingOrder) {
        Specification<JobOffer> specification = JobOfferSpecifications.createJobOfferSpecification(jobOfferSearchCriteria);

        if (pageSize > Defaults.MAX_PAGE_SIZE) pageSize = Defaults.MAX_PAGE_SIZE;
        Sort sort = resolveSorting(sortingCriteria, sortingOrder);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        return jobOfferRepository.findAll(specification, pageable).stream().toList();
    }

    /**
     * Method to retrieve job offers by search criteria and sort them by created date in ascending order.
     * 
     * @param jobOfferSearchCriteria The search criteria to filter job offers.
     * @param pageSize The number of job offers to retrieve per page.
     * @param pageNumber The page number to retrieve job offers from.
     * @return A list of job offers sorted by created date in ascending order.
     */
    public List<JobOfferDetailedView> findJobOfferByCriteriaPageAndSortByCreatedAtAscProjectedBy(JobOfferSearchCriteria jobOfferSearchCriteria, int pageSize, int pageNumber) {
        Specification<JobOffer> specification = JobOfferSpecifications.createJobOfferSpecification(jobOfferSearchCriteria);

        if (pageSize > Defaults.MAX_PAGE_SIZE) pageSize = Defaults.MAX_PAGE_SIZE;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return jobOfferRepository.findBy(specification, q -> q
                .project("name", "company.name", "address.city", "technologies.technology.name", "operatingMode", "contractType", "experience", "salary", "salaryCurrency")
                .as(JobOfferDetailedView.class)
                .page(pageable)
        ).stream().toList();
    }

    /**
     * Method to resolve the sorting criteria and order for job offers.
     * 
     * @param sortBy The criteria to sort job offers by.
     * @param sortDirection The order to sort job offers by.
     */
    private Sort resolveSorting(String sortBy, String sortDirection) {
        String field = resolveSortingCriteria(sortBy);
        String order = resolveSortingOrder(sortDirection);
        return Sort.by(Sort.Order.by(field).with(Sort.Direction.fromString(order)));
    }

    /**
     * Resolves the sorting criteria based on the given sortBy parameter.
     *
     * @param sortBy the sorting criteria to be resolved
     * @return the resolved sorting criteria as a string
     */
    private String resolveSortingCriteria(String sortBy) {
        try {
            return SortingCriteria.fromString(sortBy.toUpperCase()).toString();
        } catch (IllegalArgumentException e) {
            return SortingCriteria.CREATED_AT.toString();
        }
    }


    /**
     * Resolves the sorting order based on the given sortDirection parameter.
     *
     * @param sortDirection the sorting order to be resolved
     * @return the resolved sorting order as a string
     */
    private String resolveSortingOrder(String sortDirection) {
        try {
            return SortingOrder.fromString(sortDirection.toUpperCase()).toString();
        } catch (IllegalArgumentException e) {
            return SortingOrder.ASC.toString();
        }
    }

}

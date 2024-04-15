package com.kapia.jobboard.api.service;

import com.kapia.jobboard.api.model.JobOffer;
import com.kapia.jobboard.api.projections.JobOfferBasicView;
import com.kapia.jobboard.api.projections.JobOfferDetailedView;
import com.kapia.jobboard.api.repository.JobOfferRepository;
import com.kapia.jobboard.api.searchcriteria.JobOfferSearchCriteria;
import com.kapia.jobboard.api.specifications.JobOfferSpecifications;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class JobOfferService {

    private final JobOfferRepository jobOfferRepository;

    @Autowired
    public JobOfferService(JobOfferRepository jobOfferRepository) {
        this.jobOfferRepository = jobOfferRepository;
    }

    public Optional<JobOffer> findJobOfferById(Long id) {
        return jobOfferRepository.findById(id);
    }

    public List<JobOffer> findAllJobOffers() {
        return jobOfferRepository.findAll();
    }

    public List<JobOfferBasicView> findAllBasicProjectedBy() {
        return jobOfferRepository.findAllBasicProjectedBy();
    }

    public Optional<JobOfferBasicView> findBasicProjectedById(Long id) {
        return jobOfferRepository.findBasicProjectedById(id);
    }

    public List<JobOfferDetailedView> findAllDetailedProjectedBy() {
        return jobOfferRepository.findAllDetailedProjectedBy();
    }

    public Optional<JobOfferDetailedView> findDetailedProjectedById(Long id) {
        return jobOfferRepository.findDetailedProjectedById(id);
    }

    public List<JobOffer> findJobOfferByName(String name) {
        return jobOfferRepository.findJobOfferByName(name);
    }

    public List<JobOffer> findJobOfferByCriteria(JobOfferSearchCriteria jobOfferSearchCriteria) {
        Specification<JobOffer> specification = JobOfferSpecifications.createJobOfferSpecification(jobOfferSearchCriteria);
        return jobOfferRepository.findAll(specification);
    }

}

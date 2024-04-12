package com.kapia.jobboard.api.service;

import com.kapia.jobboard.api.model.JobOffer;
import com.kapia.jobboard.api.projections.JobOfferBasicView;
import com.kapia.jobboard.api.repository.JobOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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

    public List<JobOfferBasicView> findAllProjectedBy() {
        return jobOfferRepository.findAllProjectedBy();
    }

    public Optional<JobOfferBasicView> findProjectedById(Long id) {
        return jobOfferRepository.findProjectedById(id);
    }

    public List<JobOffer> findJobOfferByName(String name) {
        return jobOfferRepository.findJobOfferByName(name);
    }


}

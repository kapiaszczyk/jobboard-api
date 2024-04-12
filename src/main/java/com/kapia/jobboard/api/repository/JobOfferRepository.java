package com.kapia.jobboard.api.repository;

import com.kapia.jobboard.api.model.JobOffer;
import com.kapia.jobboard.api.projections.JobOfferBasicView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {

    @Query("SELECT DISTINCT j FROM job_offer j " +
            "LEFT JOIN FETCH j.company " +
            "LEFT JOIN FETCH j.technologies " +
            "LEFT JOIN FETCH j.company.addresses")
    List<JobOffer> findAll();

    List<JobOfferBasicView> findAllProjectedBy();

    Optional<JobOfferBasicView> findProjectedById(Long id);

    List<JobOffer> findJobOfferByName(String name);

}
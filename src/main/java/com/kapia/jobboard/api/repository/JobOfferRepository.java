package com.kapia.jobboard.api.repository;

import com.kapia.jobboard.api.model.JobOffer;
import com.kapia.jobboard.api.projections.JobOfferProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    List<JobOfferProjection> findAllProjectedBy();

    List<JobOffer> findJobOfferByName(String name);

    Optional<JobOfferProjection> findProjectedById(Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM job_offer j WHERE j.company.id = :companyId")
    void deleteByCompanyId(Long companyId);

    @Modifying
    @Transactional
    @Query("DELETE FROM job_offer_technology jot WHERE jot.jobOffer.id IN (SELECT j.id FROM job_offer j WHERE j.company.id = :companyId)")
    void deleteJobOfferTechnologiesByCompanyId(Long companyId);

}
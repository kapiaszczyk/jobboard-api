package com.kapia.jobboard.api.data.repository;

import com.kapia.jobboard.api.data.model.JobOffer;
import com.kapia.jobboard.api.data.projections.JobOfferBasicView;
import com.kapia.jobboard.api.data.projections.JobOfferDetailedView;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The JobOfferRepository interface is a repository interface that provides methods for managing job offers in the job board application.
 */
@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long>, JpaSpecificationExecutor<JobOffer> {

    @Query("SELECT DISTINCT j FROM job_offer j " +
            "LEFT JOIN FETCH j.company " +
            "LEFT JOIN FETCH j.technologies " +
            "LEFT JOIN FETCH j.company.addresses " +
            "LEFT JOIN FETCH j.address ")
    List<JobOffer> findAll();

    @Query("SELECT DISTINCT j FROM job_offer j " +
            "LEFT JOIN FETCH j.company " +
            "LEFT JOIN FETCH j.technologies " +
            "LEFT JOIN FETCH j.company.addresses " +
            "WHERE j.id = :id")
    Optional<JobOffer> findById(Long id);

    List<JobOfferBasicView> findAllBasicProjectedBy();

    Optional<JobOfferBasicView> findBasicProjectedById(Long id);

    @Query(
            "SELECT j " +
                    "FROM job_offer j " +
                    "LEFT JOIN FETCH j.technologies jot " +
                    "LEFT JOIN FETCH jot.technology tech " +
                    "LEFT JOIN FETCH j.address a " +
                    "LEFT JOIN FETCH j.company c "
    )
    List<JobOfferDetailedView> findAllDetailedProjectedBy();

    @Query(
            "SELECT j " +
                    "FROM job_offer j " +
                    "LEFT JOIN FETCH j.technologies jot " +
                    "LEFT JOIN FETCH jot.technology tech " +
                    "LEFT JOIN FETCH j.address a " +
                    "LEFT JOIN FETCH j.company c " +
                    "WHERE j.id = ?1 "
    )
    Optional<JobOfferDetailedView> findDetailedProjectedById(Long id);

    List<JobOffer> findJobOfferByName(String name);

    Optional<JobOfferBasicView> findProjectedById(Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM job_offer j WHERE j.company.id = :companyId")
    void deleteByCompanyId(Long companyId);

    @Modifying
    @Transactional
    @Query("DELETE FROM job_offer_technology jot WHERE jot.jobOffer.id IN (SELECT j.id FROM job_offer j WHERE j.company.id = :companyId)")
    void deleteJobOfferTechnologiesByCompanyId(Long companyId);

    @EntityGraph(attributePaths = {"company", "technologies", "company.addresses"})
    List<JobOffer> findAll(Specification<JobOffer> specification);

    @EntityGraph(attributePaths = {"company", "technologies", "company.addresses"})
    Page<JobOffer> findAll(Specification<JobOffer> specification, Pageable pageable);

}
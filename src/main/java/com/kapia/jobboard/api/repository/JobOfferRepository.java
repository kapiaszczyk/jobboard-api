package com.kapia.jobboard.api.repository;

import com.kapia.jobboard.api.model.JobOffer;
import com.kapia.jobboard.api.searchcriteria.JobOfferSearchCriteria;
import com.kapia.jobboard.api.specifications.JobOfferSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long>, JpaSpecificationExecutor<JobOffer> {

    @Query("SELECT DISTINCT j FROM job_offer j " +
            "LEFT JOIN FETCH j.company " +
            "LEFT JOIN FETCH j.technologies " +
            "LEFT JOIN FETCH j.company.addresses")
    List<JobOffer> findAll();

    default List<JobOffer> findJobOfferByCriteria(JobOfferSearchCriteria jobOfferSearchCriteria) {

        Specification<JobOffer> specification = JobOfferSpecifications.createJobOfferSpecification(jobOfferSearchCriteria);

        return findAll(specification);

    }


}
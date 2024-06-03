package com.kapia.jobboard.api.data.repository;

import com.kapia.jobboard.api.data.model.JobOfferTechnology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The JobOfferTechnologyRepository interface is a repository interface that provides methods for managing job offer technologies in the job board application.
 */
@Repository
public interface JobOfferTechnologyRepository extends JpaRepository<JobOfferTechnology, Long> {
}

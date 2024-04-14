package com.kapia.jobboard.api.repository;

import com.kapia.jobboard.api.model.JobOfferTechnology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobOfferTechnologyRepository extends JpaRepository<JobOfferTechnology, Long> {
}

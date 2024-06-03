package com.kapia.jobboard.api.data.repository;

import com.kapia.jobboard.api.data.model.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The TechnologyRepository interface is a repository interface that provides methods for managing technologies in the job board application.
 */
@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {
}

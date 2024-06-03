package com.kapia.jobboard.api.data.repository;

import com.kapia.jobboard.api.data.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * This interface is responsible for providing access to the company repository.
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    /**
     * This method is responsible for retrieving all companies.
     *
     * @return A list of all companies.
     */
    @Query("SELECT DISTINCT c FROM company c LEFT JOIN FETCH c.addresses")
    List<Company> findAll();

    /**
     * This method is responsible for retrieving a company by name.
     *
     * @param name The name of the company to retrieve.
     * @return The company with the specified name.
     */
    Optional<Company> findByName(String name);

    /**
     * This method is responsible for retrieving a company by ID.
     *
     * @param id The ID of the company to retrieve.
     * @return The company with the specified ID.
     */
    @Query("SELECT c FROM company c LEFT JOIN FETCH c.addresses WHERE c.id = :id")
    Optional<Company> findById(long id);

}

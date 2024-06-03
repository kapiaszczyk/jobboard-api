package com.kapia.jobboard.api.data.repository;

import com.kapia.jobboard.api.data.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * This interface is responsible for providing access to the address repository.
 */
public interface AddressRepository extends JpaRepository<Address, Long> {

    /**
     * This method is responsible for retrieving all addresses by company name.
     *
     * @param companyName The name of the company to retrieve addresses for.
     * @return A list of addresses for the specified company.
     */
    @Query("SELECT a FROM address a JOIN a.company c WHERE c.name = ?1")
    List<Address> findAllByCompanyName(String companyName);
}

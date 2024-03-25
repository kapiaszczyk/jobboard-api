package com.kapia.jobboard.api.repository;

import com.kapia.jobboard.api.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("SELECT a FROM address a JOIN a.company c WHERE c.name = ?1")
    List<Address> findAllByCompanyName(String companyName);
}

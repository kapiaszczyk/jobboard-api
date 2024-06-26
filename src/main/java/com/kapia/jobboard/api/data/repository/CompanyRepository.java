package com.kapia.jobboard.api.data.repository;

import com.kapia.jobboard.api.data.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT DISTINCT c FROM company c LEFT JOIN FETCH c.addresses")
    List<Company> findAll();

    Optional<Company> findByName(String name);

    @Query("SELECT c FROM company c LEFT JOIN FETCH c.addresses WHERE c.id = :id")
    Optional<Company> findById(long id);

}

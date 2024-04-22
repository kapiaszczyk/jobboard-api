package com.kapia.jobboard.api.auth.repository;

import com.kapia.jobboard.api.auth.model.AppUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    @EntityGraph(attributePaths = "role")
    Optional<AppUser> findByUsername(String username);

    @EntityGraph(attributePaths = "role")
    Optional<AppUser> findByEmail(String email);

}

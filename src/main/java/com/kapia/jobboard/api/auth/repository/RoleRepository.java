package com.kapia.jobboard.api.auth.repository;

import com.kapia.jobboard.api.auth.enums.RoleEnum;
import com.kapia.jobboard.api.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleEnum name);

}

package com.jws.medicalfile.db.repository;

import com.jws.medicalfile.db.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    Optional<Role> findById(Long id);
}

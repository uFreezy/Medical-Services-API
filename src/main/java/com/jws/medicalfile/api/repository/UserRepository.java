package com.jws.medicalfile.api.repository;


import com.jws.medicalfile.api.models.Role;
import com.jws.medicalfile.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Optional<User> findById(Long userId);

    List<User> findByRole(Role role);

    void deleteById(Long userId);
}

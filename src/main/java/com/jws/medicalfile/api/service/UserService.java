package com.jws.medicalfile.api.service;

import com.jws.medicalfile.api.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void save(User user);

    boolean delete(User user);

    User findByUsername(String username);

    Optional<User> findById(Long id);

    User getbyIndex(int indx);

    List<User> findByRole(String roleName);

    boolean any();

    long count();
}

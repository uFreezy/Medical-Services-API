package com.jws.medicalfile.db.service;


import com.jws.medicalfile.db.models.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    void save(Role role);

    boolean any();

    Role findByName(String roleName);

    Optional<Role> findById(Long id);

    List<Role> getAll();


}

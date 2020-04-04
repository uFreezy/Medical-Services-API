package com.jws.medicalfile.db.service;

import com.jws.medicalfile.db.models.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorService {
    void save(Doctor doctor);

    Optional<Doctor> findById(Long id);

    Doctor findByUsername(String username);

    Doctor findByUserId(Long userId);

    List<Doctor> getAll();

    boolean any();

    long count();

    void delete(Long id);
}

package com.jws.medicalfile.api.service;

import com.jws.medicalfile.api.models.Patient;

import java.util.List;

public interface PatientService {
    void save(Patient patient);

    Patient findByEgn(String egn);

    Patient findByUserId(Long userId);

    List<Patient> getAll();

    boolean any();

    long count();

    void delete(Long id);
}

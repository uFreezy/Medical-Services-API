package com.jws.medicalfile.db.service;

import com.jws.medicalfile.db.models.Patient;

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

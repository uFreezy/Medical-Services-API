package com.jws.medicalfile.api.service;

import com.jws.medicalfile.api.models.DoctorVisit;

import java.util.List;
import java.util.Optional;

public interface DoctorVisitService {
    void save(DoctorVisit visit);

    Optional<DoctorVisit> findById(Long id);

    List<DoctorVisit> findByDiagnosis(String diagnosis);

    List<DoctorVisit> all();

    boolean any();

    long count();

    boolean delete(Long id);
}

package com.jws.medicalfile.api.service;

import com.jws.medicalfile.api.models.SickLeave;

public interface SickLeaveService {
    void save(SickLeave sickLeave);

    SickLeave findById();

    boolean any();

    long count();

    boolean delete(Long id);
}

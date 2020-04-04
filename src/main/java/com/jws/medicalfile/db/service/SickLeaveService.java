package com.jws.medicalfile.db.service;

import com.jws.medicalfile.db.models.SickLeave;

public interface SickLeaveService {
    void save(SickLeave sickLeave);

    SickLeave findById();

    boolean any();

    long count();

    boolean delete(Long id);
}

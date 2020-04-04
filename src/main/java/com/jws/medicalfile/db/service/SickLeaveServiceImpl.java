package com.jws.medicalfile.db.service;

import com.jws.medicalfile.db.models.SickLeave;
import com.jws.medicalfile.db.repository.SickLeaveRepository;
import org.springframework.stereotype.Service;

@Service
public class SickLeaveServiceImpl implements SickLeaveService {
    private final SickLeaveRepository sickLeaveRepository;

    public SickLeaveServiceImpl(SickLeaveRepository sickLeaveRepository) {
        this.sickLeaveRepository = sickLeaveRepository;
    }

    @Override
    public void save(SickLeave sickLeave) {
        this.sickLeaveRepository.save(sickLeave);
    }

    @Override
    public SickLeave findById() {
        return null;
    }

    @Override
    public boolean any() {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public boolean delete(Long id) {
        this.sickLeaveRepository.deleteById(id);

        return !this.sickLeaveRepository.findById(id).isPresent();
    }
}

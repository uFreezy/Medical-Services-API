package com.jws.medicalfile.db.service;

import com.jws.medicalfile.db.models.DoctorVisit;
import com.jws.medicalfile.db.repository.DoctorVisitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorVisitServiceImpl implements DoctorVisitService {
    private final DoctorVisitRepository doctorVisitRepository;

    public DoctorVisitServiceImpl(DoctorVisitRepository doctorVisitRepository) {
        this.doctorVisitRepository = doctorVisitRepository;
    }

    @Override
    public void save(DoctorVisit visit) {
        this.doctorVisitRepository.save(visit);
    }

    public Optional<DoctorVisit> findById(Long id) {
        return this.doctorVisitRepository.findById(id);
    }

    @Override
    public List<DoctorVisit> findByDiagnosis(String diagnosis) {
        return this.doctorVisitRepository.findByDiagnosis(diagnosis);
    }

    @Override
    public List<DoctorVisit> all() {
        return this.doctorVisitRepository.findAll();
    }

    @Override
    public boolean any() {
        return false;
    }

    @Override
    public long count() {
        return doctorVisitRepository.count();
    }

    @Override
    public boolean delete(Long id) {
        this.doctorVisitRepository.deleteById(id);

        return !this.doctorVisitRepository.findById(id).isPresent();
    }
}

package com.jws.medicalfile.api.service;

import com.jws.medicalfile.api.models.Patient;
import com.jws.medicalfile.api.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public void save(Patient patient) {
        this.patientRepository.save(patient);
    }

    @Override
    public Patient findByEgn(String egn) {
        return this.patientRepository.findByEgn(egn);
    }

    @Override
    public Patient findByUserId(Long userId) {
        return this.patientRepository.findByUserObj_Id(userId);
    }

    @Override
    public List<Patient> getAll() {
        return this.patientRepository.findAll();
    }

    @Override
    public boolean any() {
        return this.patientRepository.count() > 0;
    }

    @Override
    public long count() {
        return this.patientRepository.count();
    }

    @Override
    public void delete(Long id) {
        this.patientRepository.deleteByUserObjId(id);
    }
}

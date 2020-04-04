package com.jws.medicalfile.db.service;

import com.jws.medicalfile.db.models.Doctor;
import com.jws.medicalfile.db.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }


    @Override
    public void save(Doctor doctor) {
        this.doctorRepository.save(doctor);
    }

    @Override
    public Optional<Doctor> findById(Long id) {
        return this.doctorRepository.findById(id);
    }

    @Override
    public Doctor findByUsername(String username) {
        return this.doctorRepository.findByUsername(username);
    }

    @Override
    public Doctor findByUserId(Long userId) {
        return this.doctorRepository.findByUserObj_Id(userId);
    }


    @Override
    public List<Doctor> getAll() {
        return this.doctorRepository.findAll();
    }

    @Override
    public boolean any() {
        return this.doctorRepository.count() > 0;
    }

    @Override
    public long count() {
        return this.doctorRepository.count();
    }

    @Override
    public void delete(Long id) {
        this.doctorRepository.deleteByUserObjId(id);
    }
}

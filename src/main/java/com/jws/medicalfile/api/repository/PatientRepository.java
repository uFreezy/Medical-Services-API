package com.jws.medicalfile.api.repository;

import com.jws.medicalfile.api.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByEgn(String egn);

    Patient findByUserObj_Id(Long userId);

    void deleteByUserObjId(Long id);
}

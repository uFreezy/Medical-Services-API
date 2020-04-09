package com.jws.medicalfile.api.repository;

import com.jws.medicalfile.api.models.DoctorVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorVisitRepository extends JpaRepository<DoctorVisit, Long> {
    @Override
    Optional<DoctorVisit> findById(Long aLong);

    List<DoctorVisit> findByDiagnosis(String diagnosis);

    void deleteById(Long userId);
}

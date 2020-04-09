package com.jws.medicalfile.api.repository;

import com.jws.medicalfile.api.models.SickLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SickLeaveRepository extends JpaRepository<SickLeave, Long> {
    void deleteById(Long id);
}

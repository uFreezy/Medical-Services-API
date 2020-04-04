package com.jws.medicalfile.db.repository;

import com.jws.medicalfile.db.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("SELECT t FROM Doctor t WHERE t.userObj.username = ?1 ")
    Doctor findByUsername(String username);

    Doctor findByUserObj_Id(Long userId);

    void deleteByUserObjId(Long id);
}

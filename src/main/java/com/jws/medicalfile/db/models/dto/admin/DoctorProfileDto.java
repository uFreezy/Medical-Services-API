package com.jws.medicalfile.db.models.dto.admin;

import com.jws.medicalfile.db.models.dto.PatientProfileDto;

import java.util.List;

public class DoctorProfileDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String specialty;

    private List<PatientProfileDto> patients;
    private List<DoctorVisitDto> visits;

    public DoctorProfileDto(Long id, String firstName, String lastName, String specialty, List<PatientProfileDto> patients, List<DoctorVisitDto> visits) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialty = specialty;
        this.patients = patients;
        this.visits = visits;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public List<PatientProfileDto> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientProfileDto> patients) {
        this.patients = patients;
    }

    public List<DoctorVisitDto> getVisits() {
        return visits;
    }

    public void setVisits(List<DoctorVisitDto> visits) {
        this.visits = visits;
    }
}

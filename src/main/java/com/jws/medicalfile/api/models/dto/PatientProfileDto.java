package com.jws.medicalfile.api.models.dto;

import java.util.Set;

public class PatientProfileDto {
    private long id;
    private String firstName;
    private String lastName;
    private String docName;
    private String egn;
    private Set<String> diagnoses;
    private Set<DoctorVisitDto> doctorVisits;

    public PatientProfileDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public PatientProfileDto(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public PatientProfileDto(String firstName, String lastName, String docName, Set<String> diagnoses) {
        this(firstName, lastName);
        this.docName = docName;
        this.diagnoses = diagnoses;
    }

    public PatientProfileDto(Long id, String firstName, String lastName, String egn, String docName, Set<String> diagnoses) {
        this(firstName, lastName);
        this.docName = docName;
        this.id = id;
        this.diagnoses = diagnoses;
        this.egn = egn;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getEgn() {
        return egn;
    }

    public void setEgn(String egn) {
        this.egn = egn;
    }

    public Set<String> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(Set<String> diagnoses) {
        this.diagnoses = diagnoses;
    }

    public Set<DoctorVisitDto> getDoctorVisits() {
        return doctorVisits;
    }

    public void setDoctorVisits(Set<DoctorVisitDto> doctorVisits) {
        this.doctorVisits = doctorVisits;
    }
}

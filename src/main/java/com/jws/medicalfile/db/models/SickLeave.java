package com.jws.medicalfile.db.models;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sick_leaves")
public class SickLeave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Doctor issuingDoctor;

    @OneToOne
    private Patient patient;

    private Date issuedFrom;

    private Date issuedTo;

    public SickLeave() {
    }

    public SickLeave(Doctor issuingDoctor, Patient patient, Date issuesFrom, Date issuedTo) {
        this.issuingDoctor = issuingDoctor;
        this.patient = patient;
        this.issuedFrom = issuesFrom;
        this.issuedTo = issuedTo;
    }

    public Doctor getIssuingDoctor() {
        return issuingDoctor;
    }

    public void setIssuingDoctor(Doctor issuingDoctor) {
        this.issuingDoctor = issuingDoctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getIssuedFrom() {
        return issuedFrom;
    }

    public void setIssuedFrom(Date issuesFrom) {
        this.issuedFrom = issuesFrom;
    }

    public Date getIssuedTo() {
        return issuedTo;
    }

    public void setIssuedTo(Date issuedTo) {
        this.issuedTo = issuedTo;
    }
}

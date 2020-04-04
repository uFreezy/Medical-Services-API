package com.jws.medicalfile.db.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jws.medicalfile.db.models.base.Person;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "patients")
public class Patient extends Person {
    @JsonProperty("egn")
    private String egn;

    private boolean paidMedInsurance;

    @OneToOne
    private User userObj;

    @ManyToOne
    @JoinColumn(name = "patients_id")
    private Doctor gpDoctor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient")
    private Set<DoctorVisit> doctorVisits;

    public Patient() {
    }

    public Patient(String firstName, String lastName, String egn, boolean paidMedInsurance, User userObj, Doctor gp) {
        super(firstName, lastName);
        if (userObj == null || !userObj.getRole().getName().equals("Patient"))
            throw new IllegalArgumentException("Only users with Patient role can be registered as patients");

        this.egn = egn;
        this.paidMedInsurance = paidMedInsurance;
        this.userObj = userObj;
        this.gpDoctor = gp;
    }

    public String getEgn() {
        return egn;
    }

    public void setEgn(String egn) {
        this.egn = egn;
    }

    public boolean isPaidMedInsurance() {
        return paidMedInsurance;
    }

    public void setPaidMedInsurance(boolean paidMedInsurance) {
        this.paidMedInsurance = paidMedInsurance;
    }

    public User getUserObj() {
        return userObj;
    }

    public void setUserObj(User userObj) {
        this.userObj = userObj;
    }

    public Doctor getGpDoctor() {
        return gpDoctor;
    }

    public void setGpDoctor(Doctor gpDoctor) {
        this.gpDoctor = gpDoctor;
    }

    public Set<DoctorVisit> getDoctorVisits() {
        return doctorVisits;
    }

    public void setDoctorVisits(Set<DoctorVisit> doctorVisits) {
        this.doctorVisits = doctorVisits;
    }


}

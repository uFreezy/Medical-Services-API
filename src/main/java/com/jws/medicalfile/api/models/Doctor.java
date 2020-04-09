package com.jws.medicalfile.api.models;

import com.jws.medicalfile.api.models.base.Person;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;

@Entity
@Table(name = "doctors")
public class Doctor extends Person {
    @NotBlank
    private String specialty;

    @OneToOne
    private User userObj;

    @OneToMany(mappedBy = "gpDoctor")
    private Set<Patient> patients;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor")
    private Set<DoctorVisit> doctorVisits;

    public Doctor() {
    }

    public Doctor(String firstName, String lastName, User userObj, String specialty) {
        super(firstName, lastName);
        this.userObj = userObj;
        this.specialty = specialty;
        this.patients = new HashSet<>();
        this.doctorVisits = new HashSet<>();
    }

    public User getUserObj() {
        return userObj;
    }

    public void setUserObj(User userObj) {
        this.userObj = userObj;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Set<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }


    public Set<DoctorVisit> getDoctorVisits() {
        return doctorVisits;
    }

    public void setDoctorVisits(Set<DoctorVisit> doctorVisits) {
        this.doctorVisits = doctorVisits;
    }

    public Set<DoctorVisit> getPastVisits() {
        return doctorVisits.stream().filter(dv -> dv.getDate().before(new Date())).collect(Collectors.toSet());
    }

    public Set<DoctorVisit> getFutureVisits() {
        return doctorVisits.stream().filter(dv -> dv.getDate().after(new Date())).collect(Collectors.toSet());
    }

    public void addPatient(Patient patient) {
        this.patients.forEach(p -> {
            if (p.getId().equals(patient.getId()))
                throw new IllegalArgumentException("Patient already in doctor's list");
        });

        this.patients.add(patient);
    }


    public void removePatient(Patient patient) {
        this.patients.remove(patient);
    }

    public void addReservation(DoctorVisit doctorVisit) {

        if (doctorVisit.getDate().before(new Date())) throw new IllegalArgumentException("OOPS");

        this.doctorVisits.forEach(r -> {

            long duration = r.getDate().getTime() - doctorVisit.getDate().getTime();

            // TODO: add before validation as well
            if (duration >= MILLISECONDS.convert(30, MINUTES)) throw new IllegalArgumentException("OOPS");
        });
        this.doctorVisits.add(doctorVisit);
    }

}

package com.jws.medicalfile.db.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "doctor_visits")
public class DoctorVisit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToOne(cascade = CascadeType.REMOVE)
    private SickLeave sickLeave;

    private String diagnosis;

    private String treatment;

    private DoctorVisitType visitType = DoctorVisitType.NEW;

    public DoctorVisit(Patient patient, Date date, Doctor doctor) {
        this(patient, date, doctor, null, null, null);
    }

    public DoctorVisit(Patient patient, Date date, Doctor doctor, String diagnosis) {
        this(patient, date, doctor, null, diagnosis, null);
    }

    public DoctorVisit(Patient patient, Date date, Doctor doctor, SickLeave sickLeave, String diagnosis) {
        this(patient, date, doctor, sickLeave, diagnosis, null);
    }

    public DoctorVisit(Patient patient, Date date, Doctor doctor, String diagnosis, String treatment) {
        this(patient, date, doctor, null, diagnosis, treatment);
    }

    public DoctorVisit() {
    }

    public DoctorVisit(Patient patient, Date date, Doctor doctor, SickLeave leave, String diagnosis, String treatment) {
        this.patient = patient;
        this.date = date;
        this.doctor = doctor;
        this.sickLeave = leave;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }

    public Long getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public SickLeave getSickLeave() {
        return sickLeave;
    }

    public void setSickLeave(SickLeave sickLeave) {
        this.sickLeave = sickLeave;
    }

    public String getDiagnosis() {
        if (this.diagnosis == null) return "None";
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        if (this.treatment == null) return "None";
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public DoctorVisitType getVisitType() {
        return visitType;
    }

    public void setVisitType(DoctorVisitType visitType) {
        this.visitType = visitType;
    }
}

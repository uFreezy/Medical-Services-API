package com.jws.medicalfile.db.models.dto;

import com.jws.medicalfile.db.models.DoctorVisitType;

import java.util.Date;

public class DoctorVisitDto {

    private Long id;

    private PatientProfileDto patient;

    private Date date;

    private DoctorInfoDto doctor;

    private Date[] sickLeave;

    private int sickLeaveDays;

    private String diagnosis;

    private String treatment;

    private DoctorVisitType visitType;

    public DoctorVisitDto(Long id, PatientProfileDto patient, Date date, DoctorInfoDto doctor, Date[] sickLeave, int sickLeaveDays, String diagnosis, String treatment, DoctorVisitType visitType) {
        this.id = id;
        this.patient = patient;
        this.date = date;
        this.doctor = doctor;
        this.sickLeave = sickLeave;
        this.sickLeaveDays = sickLeaveDays;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.visitType = visitType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PatientProfileDto getPatient() {
        return patient;
    }

    public void setPatient(PatientProfileDto patient) {
        this.patient = patient;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public DoctorInfoDto getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorInfoDto doctor) {
        this.doctor = doctor;
    }

    public Date[] getSickLeave() {
        return sickLeave;
    }

    public void setSickLeave(Date[] sickLeave) {
        this.sickLeave = sickLeave;
    }

    public int getSickLeaveDays() {
        return sickLeaveDays;
    }

    public void setSickLeaveDays(int sickLeaveDays) {
        this.sickLeaveDays = sickLeaveDays;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
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

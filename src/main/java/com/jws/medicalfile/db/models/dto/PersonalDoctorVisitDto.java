package com.jws.medicalfile.db.models.dto;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Date;

public class PersonalDoctorVisitDto {
    private Long id;
    private Date date;
    private JsonNode doctor;
    private JsonNode sickLeave;
    private String diagnosis;
    private String treatment;

    public PersonalDoctorVisitDto() {
    }

    public PersonalDoctorVisitDto(Long id, Date date, JsonNode doctor, JsonNode sickLeave, String diagnosis, String treatment) {
        this.id = id;
        this.date = date;
        this.doctor = doctor;
        this.sickLeave = sickLeave;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public JsonNode getDoctor() {
        return doctor;
    }

    public void setDoctor(JsonNode doctor) {
        this.doctor = doctor;
    }

    public JsonNode getSickLeave() {
        return sickLeave;
    }

    public void setSickLeave(JsonNode sickLeave) {
        this.sickLeave = sickLeave;
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
}

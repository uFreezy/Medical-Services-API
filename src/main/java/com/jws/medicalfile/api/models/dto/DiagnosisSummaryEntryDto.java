package com.jws.medicalfile.api.models.dto;

public class DiagnosisSummaryEntryDto {

    private String docName;
    private String patientName;
    private int sickLeaveDays;
    private String treatment;

    public DiagnosisSummaryEntryDto(String docName, String patientName, int sickLeaveDays, String treatment) {
        this.docName = docName;
        this.patientName = patientName;
        this.sickLeaveDays = sickLeaveDays;
        this.treatment = treatment;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getSickLeaveDays() {
        return sickLeaveDays;
    }

    public void setSickLeaveDays(int sickLeaveDays) {
        this.sickLeaveDays = sickLeaveDays;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
}

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


    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
}

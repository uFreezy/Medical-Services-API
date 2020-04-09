package com.jws.medicalfile.api.models.dto.admin;

public class SystemSummeryDto {
    private int userCount;
    private int patientCount;
    private int doctorCount;
    private int doctorVisitsCount;
    private String mostPopularDiagnose;

    public SystemSummeryDto(int userCount, int patientCount, int doctorCount, int doctorVisitsCount, String mostPopularDiagnose) {
        this.userCount = userCount;
        this.patientCount = patientCount;
        this.doctorCount = doctorCount;
        this.doctorVisitsCount = doctorVisitsCount;
        this.mostPopularDiagnose = mostPopularDiagnose;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public int getPatientCount() {
        return patientCount;
    }

    public void setPatientCount(int patientCount) {
        this.patientCount = patientCount;
    }

    public int getDoctorCount() {
        return doctorCount;
    }

    public void setDoctorCount(int doctorCount) {
        this.doctorCount = doctorCount;
    }

    public int getDoctorVisitsCount() {
        return doctorVisitsCount;
    }

    public void setDoctorVisitsCount(int doctorVisitsCount) {
        this.doctorVisitsCount = doctorVisitsCount;
    }

    public String getMostPopularDiagnose() {
        return mostPopularDiagnose;
    }

    public void setMostPopularDiagnose(String mostPopularDiagnose) {
        this.mostPopularDiagnose = mostPopularDiagnose;
    }
}

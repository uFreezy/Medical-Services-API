package com.jws.medicalfile.api.models.dto.admin;

import com.jws.medicalfile.api.models.Doctor;
import com.jws.medicalfile.api.models.DoctorVisit;
import com.jws.medicalfile.api.models.Patient;
import com.jws.medicalfile.api.models.SickLeave;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DoctorVisitDto {
    private String patientName;
    private String doctorName;
    private Date visitDate;
    private int sickLeaveDays;
    private String diagnosis;
    private String treatment;


    // FACEPALM
    // TODO: FIX
    public DoctorVisitDto(Patient patient, Doctor doctor, Date visitDate, SickLeave sickLeave, String diagnosis, String treatment) {
        this.patientName = patient.getFirstName() + " " + patient.getLastName();
        this.doctorName = doctor.getFirstName() + " " + doctor.getLastName();
        this.visitDate = visitDate;
        if (sickLeave != null)
            this.sickLeaveDays = (int) TimeUnit.DAYS.convert(
                    Math.abs(sickLeave.getIssuedFrom().getTime() - sickLeave.getIssuedTo().getTime()),
                    TimeUnit.MILLISECONDS);
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }

    public DoctorVisitDto(DoctorVisit visit) {
        this.patientName = visit.getPatient().getFirstName() + " " + visit.getPatient().getLastName();
        this.doctorName = visit.getDoctor().getFirstName() + " " + visit.getDoctor().getLastName();
        this.visitDate = visit.getDate();
        if (visit.getSickLeave() != null)
            this.sickLeaveDays = (int) TimeUnit.DAYS.convert(
                    Math.abs(visit.getSickLeave().getIssuedFrom().getTime() - visit.getSickLeave().getIssuedTo().getTime()),
                    TimeUnit.MILLISECONDS);
        this.diagnosis = visit.getDiagnosis();
        this.treatment = visit.getTreatment();
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
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
}

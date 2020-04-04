package com.jws.medicalfile.db.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jws.medicalfile.db.models.Doctor;
import com.jws.medicalfile.db.models.Patient;
import com.jws.medicalfile.db.models.User;
import com.jws.medicalfile.db.service.*;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    PatientService patientService;
    @Autowired
    DoctorService doctorService;
    @Autowired
    SecurityService securityService;
    @Autowired
    UserService userService;
    @Autowired
    DoctorVisitService doctorVisitService;
    @Autowired
    RoleService roleService;
    @Autowired
    SickLeaveService sickLeaveService;

    private User loggedUser;
    private Doctor loggedDoctor;
    private Patient loggedPatient;

    public User getLoggedUser() {
        if (this.loggedUser == null) this.setLoggedUser();
        return loggedUser;
    }

    private void setLoggedUser() {
        this.loggedUser = this.userService.findByUsername(this.securityService.findLoggedInUsername());
    }

    public Doctor getLoggedDoctor() {
        if (this.loggedDoctor == null) this.setLoggedDoctor();
        return loggedDoctor;
    }

    private void setLoggedDoctor() {
        this.loggedDoctor = this.doctorService.findByUserId(this.getLoggedUser().getId());
        if (this.loggedDoctor == null) throw new IllegalStateException("Logged user is not a doctor.");
    }

    public Patient getLoggedPatient() {
        if (this.loggedPatient == null) this.setLoggedPatient();
        return loggedPatient;
    }

    private void setLoggedPatient() {
        this.loggedPatient = this.patientService.findByUserId(this.getLoggedUser().getId());
        if (this.loggedPatient == null) throw new IllegalStateException("Logged user is not a patient");
    }
}

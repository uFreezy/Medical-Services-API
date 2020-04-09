package com.jws.medicalfile.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jws.medicalfile.api.models.Doctor;
import com.jws.medicalfile.api.models.Patient;
import com.jws.medicalfile.api.models.User;
import com.jws.medicalfile.api.service.*;
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

    /*private User loggedUser;
    private Doctor loggedDoctor;
    private Patient loggedPatient;*/

    public User getLoggedUser() {
        return this.userService.findByUsername(this.securityService.findLoggedInUsername());
    }


    public Doctor getLoggedDoctor() {
        return this.doctorService.findByUserId(this.getLoggedUser().getId());
    }

    public Patient getLoggedPatient() {
        return this.patientService.findByUserId(this.getLoggedUser().getId());
    }
}

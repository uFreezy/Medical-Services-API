package com.jws.medicalfile.api;

import com.jws.medicalfile.api.models.*;
import com.jws.medicalfile.api.service.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Seeder {
    private static PatientService patientService;
    private static DoctorService doctorService;
    private static UserService userService;
    private static DoctorVisitService doctorVisitService;
    private static RoleService roleService;
    private static SickLeaveService sickLeaveService;

    private static void roleSeed() {
        if (roleService.any()) return;

        Role patientRole = new Role("Patient");
        Role doctorRole = new Role("Doctor");
        Role adminRole = new Role("Admin");

        roleService.save(patientRole);
        roleService.save(doctorRole);
        roleService.save(adminRole);
    }

    private static void userSeed() {
        if (userService.any()) return;

        for (int i = 0; i < 10; i++) {
            User patient = new User("Ivan " + i,
                    "Petrov" + i,
                    "123456" + i,
                    "12345678",
                    "12345678",
                    roleService.findByName("Patient"));
            userService.save(patient);
        }

        User doctor = new User("Atanas",
                "Marinov",
                "1111111",
                "service",
                "service",
                roleService.findByName("Doctor"));

        User doctor2 = new User("Petar",
                "Dzhambazov",
                "2222222",
                "service",
                "service",
                roleService.findByName("Doctor"));

        User doctor3 = new User("Krasimira",
                "Ivanova",
                "33333333",
                "service",
                "service",
                roleService.findByName("Doctor"));

        User admin = new User("Director",
                "Director",
                "director",
                "director",
                "director",
                roleService.findByName("Admin"));


        userService.save(doctor);
        userService.save(doctor2);
        userService.save(doctor3);
        userService.save(admin);
    }

    private static void doctorSeed() {
        if (doctorService.any()) return;

        List<User> doctorUsers = userService.findByRole("Doctor");

        doctorUsers.forEach(du -> {
            Doctor doc = new Doctor(du.getFirstName(), du.getLastName(), du, "Lichen lekar");
            doctorService.save(doc);
        });
    }

    private static void patientSeed() {
        if (patientService.any()) return;

        List<User> patientUsers = userService.findByRole("Patient");

        patientUsers.forEach(pu -> {
            Doctor gpDoc = doctorService.getAll().get(0);
            Patient patient = new Patient(pu.getFirstName(), pu.getLastName(), "6711127530",
                    new Random().nextInt() % 2 == 0, pu, gpDoc);
            patientService.save(patient);
        });
    }

    private static void doctorVisitSeed() {
        if (doctorVisitService.any()) return;
        List<Patient> patients = patientService.getAll();
        List<Doctor> doctors = doctorService.getAll();

        patients.forEach(p -> {
            for (int i = 0; i < 3; i++) {
                Date date = java.util.Date
                        .from(LocalDateTime.now().plusDays(i).atZone(ZoneId.systemDefault())
                                .toInstant());
                Date dateTo = java.util.Date
                        .from(LocalDateTime.now().plusDays(new Random().nextInt(10) + i).atZone(ZoneId.systemDefault())
                                .toInstant());
                SickLeave sickLeave = new SickLeave(doctors.get(i), p, date, dateTo);
                sickLeaveService.save(sickLeave);
                DoctorVisit visit = new DoctorVisit(p, date, doctors.get(i), sickLeave, "Flu");
                visit.setVisitType(DoctorVisitType.NEW);
                doctorVisitService.save(visit);
            }
        });
    }

    public static void run(RoleService roleService, UserService userService,
                           DoctorService doctorService, PatientService patientService,
                           DoctorVisitService doctorVisitService, SickLeaveService sickLeaveService) {
        Seeder.roleService = roleService;
        Seeder.userService = userService;
        Seeder.doctorService = doctorService;
        Seeder.patientService = patientService;
        Seeder.doctorVisitService = doctorVisitService;
        Seeder.sickLeaveService = sickLeaveService;


        Seeder.roleSeed();
        Seeder.userSeed();
        Seeder.doctorSeed();
        Seeder.patientSeed();
        Seeder.doctorVisitSeed();

    }
}
package com.jws.medicalfile;

import com.jws.medicalfile.db.Seeder;
import com.jws.medicalfile.db.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MedicalfileApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalfileApplication.class, args);
    }

    @Bean
    public CommandLineRunner seedRunner(RoleService roleRepo, UserService userService, DoctorService doctorService, PatientService patientService, DoctorVisitService visitService, SickLeaveService sickLeaveService) {
        return (args) ->
                Seeder.run(roleRepo, userService, doctorService, patientService, visitService, sickLeaveService);
    }
}

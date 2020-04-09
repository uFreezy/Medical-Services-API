package com.jws.medicalfile.api.controller;

import com.jws.medicalfile.api.models.Doctor;
import com.jws.medicalfile.api.models.dto.DoctorInfoDto;
import com.jws.medicalfile.api.models.dto.admin.RoleInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/utility")
public class UtilityController extends BaseController {

    @GetMapping("/roles")
    public ResponseEntity<Object> getRoles() {
        List<RoleInfoDto> roleInfo = new ArrayList<>();

        this.roleService.getAll().forEach(r -> roleInfo.add(new RoleInfoDto(r.getId(), r.getName())));

        return ResponseEntity.ok(roleInfo);
    }

    @GetMapping("/loggedrole")
    public ResponseEntity<Object> getLoggedUserRole() {
        return ResponseEntity.ok(super.getLoggedUser().getRole());
    }

    @GetMapping("/docinfo")
    public ResponseEntity<Object> doctorInfo() {
        List<Doctor> doc = this.doctorService.getAll();
        List<DoctorInfoDto> doctorInfoDtoList = new ArrayList<>();

        doc.forEach(d -> doctorInfoDtoList
                .add(new DoctorInfoDto(d.getId(), d.getFirstName() + " " + d.getLastName(), d.getSpecialty())));

        return ResponseEntity.ok(doctorInfoDtoList);
    }
}

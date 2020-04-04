package com.jws.medicalfile.db.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jws.medicalfile.db.models.Doctor;
import com.jws.medicalfile.db.models.DoctorVisit;
import com.jws.medicalfile.db.models.DoctorVisitType;
import com.jws.medicalfile.db.models.Patient;
import com.jws.medicalfile.db.models.dto.PatientProfileDto;
import com.jws.medicalfile.db.models.dto.PersonalDoctorVisitDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.json.JSONWriter.valueToString;

@RestController
@RequestMapping("/patient")
@PreAuthorize("hasAuthority('Patient')")
public class PatientController extends BaseController {

    @PostMapping(value = "/visit")
    public ResponseEntity<Object> doctorVisit(@RequestParam String visitDate, @RequestParam Long docId) {
        Date pVisitDate;

        try {
            pVisitDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(visitDate);
        } catch (ParseException e) {
            return ResponseEntity.unprocessableEntity().body(valueToString("Can't parse visit date"));
        }

        if (pVisitDate.before(new Date()))
            return ResponseEntity.badRequest().body(valueToString("Visit Date can't be in the past"));

        Optional<Doctor> doc = this.doctorService.findById(docId);

        if (!doc.isPresent()) return ResponseEntity.badRequest().body(valueToString("Doctor not found."));

        DoctorVisit dv = new DoctorVisit(getLoggedPatient(), pVisitDate, doc.get());
        dv.setVisitType(DoctorVisitType.NEW);

        this.doctorVisitService.save(dv);

        return ResponseEntity.ok(valueToString("Doctor visit with ID: " + dv.getId() + " registered successfully."));
    }

    @GetMapping("/visits")
    public ResponseEntity<Object> personalVisits() throws JsonProcessingException {
        Set<DoctorVisit> visits = getLoggedPatient().getDoctorVisits();

        List<PersonalDoctorVisitDto> visitsDto = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        for (DoctorVisit visit : visits) {
            PersonalDoctorVisitDto vDto = new PersonalDoctorVisitDto();
            vDto.setId(visit.getId());
            vDto.setDate(visit.getDate());
            Long docId = visit.getDoctor().getUserObj().getId();
            String docName = visit.getDoctor().getFirstName() + " " + visit.getDoctor().getLastName();
            vDto.setDoctor(mapper.readTree("{ \"id\" : \"" + docId + "\", \"name\": \"" + docName + "\"}"));
            if (visit.getSickLeave() != null) {
                Date from = visit.getSickLeave().getIssuedFrom();
                Date to = visit.getSickLeave().getIssuedTo();
                long days = TimeUnit.DAYS.convert((to.getTime() - from.getTime()), TimeUnit.MILLISECONDS);
                vDto.setSickLeave(mapper.readTree("{ \"from\" : \"" + from + "\", \"to\" : \"" + to + "\", \"days\" : \" " + days + "\"}"));
            }
            vDto.setDiagnosis(visit.getDiagnosis());
            vDto.setTreatment(visit.getTreatment());

            visitsDto.add(vDto);
        }

        return ResponseEntity.ok(visitsDto);
    }

    @GetMapping("/profile")
    public ResponseEntity<Object> getPatientProfile() {
        Set<String> diagnoses = new HashSet<>();
        getLoggedPatient().getDoctorVisits().forEach(dv -> diagnoses.add(dv.getDiagnosis()));

        PatientProfileDto profile = new PatientProfileDto(getLoggedPatient().getFirstName(), getLoggedPatient().getLastName(),
                getLoggedPatient().getGpDoctor().getFirstName() + " " + getLoggedPatient().getGpDoctor().getLastName(), diagnoses);
        profile.setEgn(getLoggedPatient().getEgn());

        return ResponseEntity.ok(profile);
    }


    @PutMapping("/editprofile")
    public ResponseEntity<Object> editPatientProfile(@RequestBody String userInfo) {
        Patient updatedPatient;
        try {
            updatedPatient = objectMapper.readerForUpdating(getLoggedPatient()).readValue(userInfo);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(valueToString("Invalid JSON string!"));
        }

        this.patientService.save(updatedPatient);
        return ResponseEntity.ok(valueToString("Profile updated successfully!"));
    }
}

package com.jws.medicalfile.db.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.jws.medicalfile.db.models.*;
import com.jws.medicalfile.db.models.dto.PatientProfileDto;
import com.jws.medicalfile.db.models.dto.admin.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.json.JSONWriter.valueToString;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('Admin')")
public class AdminController extends BaseController {

    @PostMapping("/registeruser")
    public ResponseEntity<Object> registration(@RequestBody String userInfo) throws JsonProcessingException {
        User newUser = new User();
        JsonNode accountTypeNode = objectMapper.readTree(userInfo).get("account_type");
        JsonNode insuranceNode = objectMapper.readTree(userInfo).get("paid_insurance");
        JsonNode egnNode = objectMapper.readTree(userInfo).get("egn");
        JsonNode docIdNode = objectMapper.readTree(userInfo).get("doc_id");
        JsonNode specialtyNode = objectMapper.readTree(userInfo).get("specialty");
        userInfo = userInfo.replace("\"account_type\":" + accountTypeNode.toString() + ",", "");
        userInfo = "{\"first_name\": \"" + objectMapper.readTree(userInfo).get("first_name").asText() +
                "\", \"last_name\": \"" + objectMapper.readTree(userInfo).get("last_name").asText() + "\"," +
                "\"username\": \"" + objectMapper.readTree(userInfo).get("username").asText() + "\" }";

        try {
            objectMapper.readerForUpdating(newUser).readValue(userInfo);
        } catch (JsonProcessingException e) {
            return ResponseEntity.unprocessableEntity().body("Bad User JSON");
        }

        // GENERATE RANDOM PASSWORD FOR THE NEW ACC
        char[] possibleCharacters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?").toCharArray();
        String randomStr = RandomStringUtils.random(30, 0,
                possibleCharacters.length - 1, false,
                false, possibleCharacters, new SecureRandom());

        newUser.setPassword(randomStr);
        if (accountTypeNode.asInt() == 0) newUser.setRole(this.roleService.findByName("Patient"));
        else newUser.setRole(this.roleService.findByName("Doctor"));

        this.userService.save(newUser);

        if (accountTypeNode.asInt() == 0) {
            Doctor doc = this.doctorService.findByUserId(docIdNode.asLong());
            Patient patient = new Patient(newUser.getFirstName(), newUser.getLastName(), egnNode.asText(), insuranceNode.asBoolean(), newUser, doc);
            this.patientService.save(patient);
        } else {
            Doctor doc = new Doctor(newUser.getFirstName(), newUser.getLastName(), newUser, specialtyNode.asText());
            this.doctorService.save(doc);
        }

        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/patientlist")
    public ResponseEntity<Object> listPatients(@RequestParam(required = false) Long patientId) {
        if (patientId != null) {
            Patient patient = this.patientService.findByUserId(patientId);

            if (patient == null)
                return ResponseEntity.badRequest().body("Can't find patient with userId = " + patientId.toString());

            PatientInfoDto pInfo = new PatientInfoDto(patient.getUserObj().getId(), patient.getFirstName(), patient.getLastName(),
                    patient.getEgn(), patient.getUserObj().getUsername(),
                    patient.isPaidMedInsurance(), patient.getDoctorVisits().size(),
                    patient.getGpDoctor().getFirstName() + " " + patient.getGpDoctor().getLastName());

            return ResponseEntity.ok(pInfo);
        }

        List<Patient> allPatients = this.patientService.getAll();
        List<PatientInfoDto> patientInfoList = new ArrayList<>();

        allPatients.forEach(p -> {
            PatientInfoDto pInfo = new PatientInfoDto(p.getUserObj().getId(), p.getFirstName(), p.getLastName(),
                    p.getEgn(), p.getUserObj().getUsername(),
                    p.isPaidMedInsurance(), p.getDoctorVisits().size(), null);
            if (p.getGpDoctor() != null) {
                pInfo.setGpName(p.getGpDoctor().getFirstName() + " " + p.getGpDoctor().getLastName());
            } else {
                pInfo.setGpName("None");
            }

            patientInfoList.add(pInfo);
        });

        return ResponseEntity.ok(patientInfoList);
    }

    @GetMapping("/doctorlist")
    public ResponseEntity<Object> listDoctors(@RequestParam(required = false) Long doctorId) {
        if (doctorId != null) {
            Doctor doctor = this.doctorService.findByUserId(doctorId);

            if (doctor == null)
                return ResponseEntity.badRequest().body("Can't find doctor with userId = " + doctorId.toString());

            DoctorInfoDto dInfo = new DoctorInfoDto(doctor.getUserObj().getId(), doctor.getFirstName(), doctor.getLastName(), doctor.getUserObj().getUsername(),
                    doctor.getPatients().size(), doctor.getDoctorVisits().size());

            return ResponseEntity.ok(dInfo);
        }

        List<Doctor> allDoctors = this.doctorService.getAll();
        List<DoctorInfoDto> doctorInfoList = new ArrayList<>();

        allDoctors.forEach(d ->
                doctorInfoList.add(new DoctorInfoDto(d.getUserObj().getId(), d.getFirstName(), d.getLastName(), d.getUserObj().getUsername(),
                        d.getPatients().size(), d.getDoctorVisits().size())));


        return ResponseEntity.ok(doctorInfoList);
    }

    @GetMapping("/visitlist")
    public ResponseEntity<Object> listDoctorVisits(@RequestParam(required = false) Long visitId) {
        if (visitId != null) {
            if (!this.doctorVisitService.findById(visitId).isPresent())
                return ResponseEntity.badRequest().body("Can't find visit with id = " + visitId.toString());

            DoctorVisit dvRaw = this.doctorVisitService.findById(visitId).get();
            DoctorVisitDto visit = new DoctorVisitDto(dvRaw.getPatient(), dvRaw.getDoctor(),
                    dvRaw.getDate(), dvRaw.getSickLeave(),
                    dvRaw.getDiagnosis(), dvRaw.getTreatment());

            return ResponseEntity.ok(visit);
        }

        List<DoctorVisitDto> visits = new ArrayList<>();
        for (DoctorVisit dvRaw : this.doctorVisitService.all()) {
            visits.add(new DoctorVisitDto(dvRaw.getPatient(), dvRaw.getDoctor(),
                    dvRaw.getDate(), dvRaw.getSickLeave(),
                    dvRaw.getDiagnosis(), dvRaw.getTreatment()));
        }

        return ResponseEntity.ok(visits);
    }

    @DeleteMapping("/delvisit")
    public ResponseEntity<Object> deleteVisit(@RequestParam Long visitId) {
        boolean isDeleted = this.doctorVisitService.delete(visitId);

        if (isDeleted)
            return ResponseEntity.ok("Doctor visit with id: " + visitId + " has been deleted successfully");
        else
            return ResponseEntity
                    .unprocessableEntity().body("Deleting doctor visit with id: " + visitId + " has failed!");
    }

    // TODO: Delete & edit doctor visits
    @PutMapping("/editvisit")
    public ResponseEntity<Object> editVisit() {
        return ResponseEntity.ok(valueToString(""));
    }


    @DeleteMapping("/removesickleave")
    public ResponseEntity<Object> deleteSickLeave(@RequestParam Long leaveId) {
        boolean isDeleted = this.sickLeaveService.delete(leaveId);

        if (isDeleted)
            return ResponseEntity.ok("Sick leave with id: " + leaveId + " has been deleted successfully");
        else
            return ResponseEntity
                    .unprocessableEntity().body("Deleting doctor visit with id: " + leaveId + " has failed!");
    }

    @PutMapping("/editsickleave")
    public ResponseEntity<Object> editSickLeave(@RequestParam Long id, @RequestParam String dateFrom, @RequestParam String dateTo) {
        SickLeave sl = this.sickLeaveService.findById();

        if (dateFrom != null) {
            try {
                sl.setIssuedFrom(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateFrom));
            } catch (ParseException e) {
                return ResponseEntity.unprocessableEntity().body(valueToString("Can't parse from date"));
            }
        }
        if (dateTo != null) {
            try {
                sl.setIssuedTo(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateTo));
            } catch (ParseException e) {
                return ResponseEntity.unprocessableEntity().body(valueToString("Can't parse to date"));
            }
        }

        this.sickLeaveService.save(sl);

        return ResponseEntity.ok(valueToString("Sick leave with id: " + id + " updated successfully!"));
    }

    @Transactional
    @PutMapping("/userrole")
    public ResponseEntity<Object> changeUserRole(@RequestParam Long userId, Long roleId) {
        if (!this.userService.findById(userId).isPresent())
            return ResponseEntity.badRequest().body(JSONObject.valueToString("Can't find user with id: " + userId.toString()));
        if (!this.roleService.findById(roleId).isPresent())
            return ResponseEntity.badRequest().body(JSONObject.valueToString("Can't find role with id: " + roleId.toString()));

        User user = this.userService.findById(userId).get();

        if (user.getRole().getName().equals("Patient")) {
            this.patientService.delete(userId);
            Doctor newEntity = new Doctor(user.getFirstName(), user.getLastName(), user, "N/A");
            this.doctorService.save(newEntity);
        } else {
            this.doctorService.delete(userId);
            Patient newEntitiy = new Patient(user.getFirstName(), user.getLastName(), "N/A", true, user, null);
            this.patientService.save(newEntitiy);
        }


        user.setRole(this.roleService.findById(roleId).get());

        this.userService.save(user);

        return ResponseEntity.ok(
                JSONObject.valueToString("User role changed successfully! UserId: " + userId + " RoleId: " + roleId));
    }

    @DeleteMapping("/removeuser")
    public ResponseEntity<Object> deleteUser(@RequestParam Long userId) {
        if (!this.userService.findById(userId).isPresent())
            return ResponseEntity.badRequest().body("Can't find user with id = " + userId.toString());

        User user = this.userService.findById(userId).get();

        if (user.getRole().getName().equals("Patient"))
            this.patientService.delete(userId);
        else
            this.doctorService.delete(userId);

        boolean isDeleted = this.userService.delete(user);

        if (isDeleted)
            return ResponseEntity.ok("User " + user.getUsername() + " was deleted successfully!");
        else
            return ResponseEntity.badRequest().body("Couldn't delete user " + user.getUsername());
    }

    @GetMapping("/profile")
    public ResponseEntity<Object> getProfile(@RequestParam Long userId) {
        User usr = this.userService.findById(userId).get();

        if (usr.getRole().getName().equals("Patient")) {
            Patient user = this.patientService.findByUserId(userId);

            Set<String> diagnoses = new HashSet<>();

            user.getDoctorVisits().forEach(dv -> diagnoses.add(dv.getDiagnosis()));

            PatientProfileDto profile = new PatientProfileDto(user.getFirstName(), user.getLastName(),
                    user.getGpDoctor().getFirstName() + " " + user.getGpDoctor().getLastName(), diagnoses);
            profile.setEgn(user.getEgn());

            return ResponseEntity.ok(profile);
        } else {
            Doctor doc = this.doctorService.findByUserId(userId);

            List<PatientProfileDto> patients = new ArrayList<>();
            doc.getPatients().forEach(p ->
                    patients.add(new PatientProfileDto(p.getUserObj().getId(), p.getFirstName(), p.getFirstName())));

            List<DoctorVisitDto> visits = new ArrayList<>();
            doc.getDoctorVisits().forEach(v -> visits.add(new DoctorVisitDto(v)));

            DoctorProfileDto doctorProfile =
                    new DoctorProfileDto(doc.getUserObj().getId(), doc.getFirstName(),
                            doc.getLastName(), doc.getSpecialty(), patients, visits);

            return ResponseEntity.ok(doctorProfile);
        }
    }

    @PutMapping("/editprofile")
    public ResponseEntity<Object> editProfile(@RequestBody String userInfo) {
        try {
            JsonNode idNode = objectMapper.readTree(userInfo).get("id");
            Role userRole = this.userService.findById(idNode.asLong()).get().getRole();

            userInfo = userInfo.replace("\"id\":" + idNode.toString() + ",", "");

            if (userRole.getName().equals("Doctor")) {
                Doctor updatedDoc = objectMapper.readerForUpdating(this.doctorService.findByUserId(idNode.asLong()))
                        .readValue(userInfo);

                this.doctorService.save(updatedDoc);

                return ResponseEntity.ok(valueToString("Profile updated successfully!"));
            }

            JsonNode doctorIdNode = objectMapper.readTree(userInfo).get("doctor_id");

            Long patientId = idNode.longValue();
            Patient patient = this.patientService.findByUserId(patientId);

            if (doctorIdNode != null) userInfo = userInfo.replace(",\"doctor_id\":" + doctorIdNode.toString(), "");

            Patient updatedPatient = objectMapper.readerForUpdating(patient).readValue(userInfo);

            assert doctorIdNode != null;
            if (doctorIdNode.getNodeType() != NullNode.getInstance().getNodeType()) {
                Doctor oldDoc = patient.getGpDoctor();
                oldDoc.removePatient(patient);
                Doctor newDoctor = this.doctorService.findById(doctorIdNode.longValue()).get();
                newDoctor.addPatient(updatedPatient);

                updatedPatient.setGpDoctor(newDoctor);

                this.doctorService.save(oldDoc);
                this.doctorService.save(newDoctor);
            }

            this.patientService.save(updatedPatient);

        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(valueToString("Invalid JSON string!"));
        }

        return ResponseEntity.ok(valueToString("Profile updated successfully!"));
    }


    @GetMapping("/summary")
    public ResponseEntity<Object> systemSummary() {
        int usersCount = (int) this.userService.count();
        int patientCount = (int) this.patientService.count();
        int doctorCount = (int) this.doctorService.count();
        int doctorVisitsCount = (int) this.doctorVisitService.count();

        // This is dog slow. Better approach would be to have the diagnose stats in the DB
        // and just pull them from there.
        String mostPopularDiagnose = this.doctorVisitService.all().stream()
                // map person to tag & filter null tag out
                .map(DoctorVisit::getDiagnosis).filter(Objects::nonNull)
                // summarize tags
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                // fetch the max entry
                .entrySet().stream().max(Map.Entry.comparingByValue())
                // map to tag
                .map(Map.Entry::getKey).orElse(null);

        SystemSummeryDto summary =
                new SystemSummeryDto(usersCount, patientCount, doctorCount, doctorVisitsCount, mostPopularDiagnose);

        return ResponseEntity.ok(summary);
    }
}

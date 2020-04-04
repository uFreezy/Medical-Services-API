package com.jws.medicalfile.db.controller;

import com.jws.medicalfile.db.models.DoctorVisit;
import com.jws.medicalfile.db.models.DoctorVisitType;
import com.jws.medicalfile.db.models.Patient;
import com.jws.medicalfile.db.models.SickLeave;
import com.jws.medicalfile.db.models.dto.DiagnonsisSummaryDto;
import com.jws.medicalfile.db.models.dto.DiagnosisSummaryEntryDto;
import com.jws.medicalfile.db.models.dto.DoctorVisitDto;
import com.jws.medicalfile.db.models.dto.PatientProfileDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.lang.Long.parseLong;
import static org.json.JSONObject.valueToString;

@RestController
@RequestMapping("/doctor")
@PreAuthorize("hasAuthority('Doctor')")
public class DoctorController extends BaseController {

    @GetMapping("/pendingvisits")
    public ResponseEntity<Object> pendingVisits() {
        if (getLoggedDoctor().getFutureVisits().isEmpty()) return ResponseEntity.ok(valueToString("No pending visits"));

        Set<DoctorVisitDto> pendingVisits = new HashSet<>();

        getLoggedDoctor().getFutureVisits().forEach(v -> {
            PatientProfileDto patientDto =
                    new PatientProfileDto(v.getPatient().getFirstName(), v.getPatient().getLastName());
            DoctorVisitDto dv =
                    new DoctorVisitDto(v.getId(), patientDto, v.getDate(), null, null, 0, null, null, v.getVisitType());

            pendingVisits.add(dv);

        });

        return ResponseEntity.ok(pendingVisits.toArray());
    }

    @GetMapping("/pastvisits")
    public ResponseEntity<Object> pastVisits() {
        if (getLoggedDoctor().getPastVisits().isEmpty()) return ResponseEntity.ok(valueToString("No past visits"));

        List<DoctorVisitDto> visits = new ArrayList<>();
        getLoggedDoctor().getPastVisits().forEach(v -> {
            Date from = v.getSickLeave().getIssuedFrom();
            Date to = v.getSickLeave().getIssuedTo();
            long days = TimeUnit.DAYS.convert((to.getTime() - from.getTime()), TimeUnit.MILLISECONDS);
            PatientProfileDto patientDto =
                    new PatientProfileDto(v.getPatient().getFirstName(), v.getPatient().getLastName());
            DoctorVisitDto dv =
                    new DoctorVisitDto(v.getId(), patientDto, v.getDate(), null, null, (int) days, v.getDiagnosis(), v.getTreatment(), v.getVisitType());
            visits.add(dv);
        });

        return ResponseEntity.ok(visits);
    }

    @PutMapping("/processvisit")
    public ResponseEntity<Object> processVisit(@RequestParam String visitId,
                                               @RequestParam String diagnosis,
                                               @RequestParam(required = false) String sickLeaveFrom,
                                               @RequestParam(required = false) String sickLeaveTo,
                                               @RequestParam(required = false) String treatment) {
        if (!this.doctorVisitService.findById(parseLong(visitId)).isPresent())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(valueToString("Visit can't be fetched"));

        DoctorVisit dv = this.doctorVisitService.findById(parseLong(visitId)).get();
        dv.setDiagnosis(diagnosis);

        if ((sickLeaveFrom != null && sickLeaveTo == null) || (sickLeaveFrom == null && sickLeaveTo != null))
            return ResponseEntity.badRequest()
                    .body(valueToString("Both sick leave dates are required in order to process entity"));

        Date sickLeaveFromDate;
        Date sickLeaveToDate;
        try {
            sickLeaveFromDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(sickLeaveFrom);
            sickLeaveToDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(sickLeaveTo);
        } catch (ParseException e) {
            return ResponseEntity.unprocessableEntity().body(valueToString(e.getMessage()));

        }

        SickLeave sl = new SickLeave(dv.getDoctor(), dv.getPatient(), sickLeaveFromDate, sickLeaveToDate);

        this.sickLeaveService.save(sl);

        dv.setSickLeave(sl);
        dv.setVisitType(DoctorVisitType.FINISHED);
        this.doctorVisitService.save(dv);

        return ResponseEntity.ok(valueToString("Doctor visit with ID: " + visitId + " has been processed successfully."));
    }

    @PutMapping("/declinevisit")
    public ResponseEntity<Object> declineVisit(@RequestParam Long visitId) {
        Optional<DoctorVisit> dv = this.doctorVisitService.findById(visitId);

        if (!dv.isPresent())
            return ResponseEntity.badRequest().body(valueToString("Can't find doctor visit with ID:" + visitId));
        if (dv.get().getVisitType() != DoctorVisitType.NEW)
            return ResponseEntity.badRequest().body(valueToString("This doctor visit is already processed or declined."));

        dv.get().setVisitType(DoctorVisitType.CANCELLED);
        this.doctorVisitService.save(dv.get());

        return ResponseEntity.ok(valueToString("Visit cancelled successfully! ID: " + visitId));
    }

    private Set<String> getDiagnoses() {
        Set<String> diagnoses = new HashSet<>();
        this.doctorVisitService.all().forEach(dv -> diagnoses.add(dv.getDiagnosis()));

        return diagnoses;
    }

    @GetMapping("/getdiagnosesummary")
    public ResponseEntity<Object> getPatientsWithSpecificDiagnose() {
        Set<String> diagnoses = this.getDiagnoses();
        List<DiagnonsisSummaryDto> diagnosesSummaries = new ArrayList<>();

        diagnoses.forEach(diagnosis -> {
            List<DoctorVisit> dvs = this.doctorVisitService.findByDiagnosis(diagnosis);

            DiagnonsisSummaryDto ds = new DiagnonsisSummaryDto(diagnosis);

            for (DoctorVisit dv : dvs) {
                String patientName = dv.getPatient().getFirstName() + " " + dv.getPatient().getLastName();
                String doctorName = dv.getDoctor().getFirstName() + " " + dv.getDoctor().getLastName();


                long sickLeaveDays = TimeUnit.DAYS.convert((dv.getSickLeave().getIssuedTo().getTime()
                        - dv.getSickLeave().getIssuedFrom().getTime()), TimeUnit.MILLISECONDS);

                DiagnosisSummaryEntryDto entry =
                        new DiagnosisSummaryEntryDto(doctorName, patientName, (int) sickLeaveDays, dv.getTreatment());


                ds.addSicknessEntry(entry);
            }
            diagnosesSummaries.add(ds);
        });

        return ResponseEntity.ok(diagnosesSummaries);
    }

    @GetMapping("/managed")
    public ResponseEntity<Object> getManagedPatients() {
        List<PatientProfileDto> personalP = new ArrayList<>();

        getLoggedDoctor().getPatients()
                .forEach(p -> personalP.add(new PatientProfileDto(p.getUserObj().getId(),
                        p.getFirstName(), p.getLastName(),
                        p.getEgn(), null, null)));

        return ResponseEntity.ok(personalP);
    }

    @GetMapping("/patientinfo")
    public ResponseEntity<Object> patientInfo() {
        List<Patient> patientList = this.patientService.getAll();
        List<PatientProfileDto> patientProfileDtoList = new ArrayList<>();

        for (Patient p : patientList) {
            if (getLoggedDoctor().getPatients().contains(p)) continue;
            patientProfileDtoList.add(new PatientProfileDto(p.getUserObj().getId(), p.getFirstName(), p.getLastName()));
        }

        return ResponseEntity.ok(patientProfileDtoList);
    }

    @PutMapping("/addmanagedpatient")
    public ResponseEntity<Object> addManagedPatient(@RequestParam Long patientUserId) {
        if (this.patientService.findByUserId(patientUserId) == null)
            return ResponseEntity.badRequest().body(valueToString("Patient with userId: " + patientUserId + " not found."));

        Patient p = this.patientService.findByUserId(patientUserId);
        getLoggedDoctor().addPatient(this.patientService.findByUserId(patientUserId));
        p.setGpDoctor(getLoggedDoctor());

        this.doctorService.save(getLoggedDoctor());
        this.patientService.save(p);

        return ResponseEntity.ok(valueToString("Patient added successfully!"));
    }

    @PutMapping("/removemanagedpatient")
    public ResponseEntity<Object> removeManagedPatient(@RequestParam Long patientUserId) {
        if (this.patientService.findByUserId(patientUserId) == null)
            return ResponseEntity.badRequest().body(valueToString("Patient with userId: " + patientUserId + " not found."));

        Patient p = this.patientService.findByUserId(patientUserId);
        getLoggedDoctor().removePatient(p);
        p.setGpDoctor(null);

        this.doctorService.save(getLoggedDoctor());
        this.patientService.save(p);

        return ResponseEntity.ok(valueToString("Patient removed successfully!"));
    }
}

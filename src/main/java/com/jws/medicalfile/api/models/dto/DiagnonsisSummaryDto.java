package com.jws.medicalfile.api.models.dto;

import java.util.ArrayList;
import java.util.List;

public class DiagnonsisSummaryDto {

    private String diagnosisName;

    private final List<DiagnosisSummaryEntryDto> sicknessEntries;

    public DiagnonsisSummaryDto(String diagnosisName) {
        this.diagnosisName = diagnosisName;
        this.sicknessEntries = new ArrayList<>();
    }

    public void addSicknessEntry(DiagnosisSummaryEntryDto entry) {
        this.sicknessEntries.add(entry);
    }

    public String getDiagnosisName() {
        return diagnosisName;
    }

    public List<DiagnosisSummaryEntryDto> getSicknessEntries() {
        return sicknessEntries;
    }
}

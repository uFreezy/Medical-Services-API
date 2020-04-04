package com.jws.medicalfile.db.models.dto;

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
}

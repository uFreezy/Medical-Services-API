package com.jws.medicalfile.api.models.dto;

import java.util.ArrayList;
import java.util.List;

public class DiagnonsisSummaryDto {

    private final List<DiagnosisSummaryEntryDto> sicknessEntries;

    public DiagnonsisSummaryDto(String diagnosisName) {
        this.sicknessEntries = new ArrayList<>();
    }

    public void addSicknessEntry(DiagnosisSummaryEntryDto entry) {
        this.sicknessEntries.add(entry);
    }
}

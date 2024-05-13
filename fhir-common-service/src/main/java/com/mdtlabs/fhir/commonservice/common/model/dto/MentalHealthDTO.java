package com.mdtlabs.fhir.commonservice.common.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * This DTO class handling the MentalHealth details
 * </p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MentalHealthDTO {

    private Long id;

    private Long tenantId;

    private String phq9RiskLevel;

    private Integer phq9Score;

    private String gad7RiskLevel;

    private Integer gad7Score;

    private String phq4RiskLevel;

    private Integer phq4Score;

    private Integer phq4FirstScore;

    private Integer phq4SecondScore;

    private Long patientTrackId;

    private List<MentalHealthDetailsDTO> phq9MentalHealth;

    private List<MentalHealthDetailsDTO> gad7MentalHealth;

    private List<MentalHealthDetailsDTO> phq4MentalHealth;

    private Long assessmentTenantId;

    private Map<String, String> suicideScreener;

    private String suicidalIdeation;

    private Map<String, String> substanceAbuse;

    private Integer cageAid;

    public MentalHealthDTO(String phq4RiskLevel, Integer phq4Score) {
        this.phq4RiskLevel = phq4RiskLevel;
        this.phq4Score = phq4Score;
    }

    public MentalHealthDTO() {
    }

}
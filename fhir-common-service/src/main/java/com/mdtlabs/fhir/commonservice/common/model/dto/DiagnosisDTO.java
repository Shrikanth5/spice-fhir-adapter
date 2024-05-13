package com.mdtlabs.fhir.commonservice.common.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This DTO class handling the Diagnosis details
 * </p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
public class DiagnosisDTO {

    private Integer htnYearOfDiagnosis;

    private Integer diabetesYearOfDiagnosis;

    private String htnPatientType;

    private String diabetesPatientType;

    private String diabetesDiagnosis;

    private String diabetesDiagControlledType;

    private Boolean isHtnDiagnosis;

    private Boolean isDiabetesDiagnosis;

    private String htnDiagnosis;

    private List<String> confirmDiagnosis = new ArrayList<>();

    private String diagnosisDiabetes; // For auto populate screened values in follow up enrollment

    private String diagnosisHypertension;  // For auto populate screened values in follow up enrollment

    private List<String> mentalHealthDiagnosis;

    private List<String> substanceDisorderDiagnosis;

}
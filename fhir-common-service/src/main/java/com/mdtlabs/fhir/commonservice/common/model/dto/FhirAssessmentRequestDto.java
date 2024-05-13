package com.mdtlabs.fhir.commonservice.common.model.dto;

import lombok.Data;

import java.util.List;

/**
 * <p>
 * This DTO class handling the FhirAssessmentRequest details
 * </p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
public class FhirAssessmentRequestDto {

    private String type;

    private BpLogDTO bpLog;

    private GlucoseLogDTO glucoseLog;

    private Long patientTrackId;

    private Long spiceBpLogId;

    private Long spiceGlucoseLogId;

    private Long createdBy;

    private Long updatedBy;

    private MentalHealthDTO mentalHealth;

    private List<PatientSymptom> patientSymptomList;

    private PatientPregnancyDetails patientPregnancyDetails;

    private RedRiskNotification redRiskNotification;

    private List<PatientMedicalCompliance> patientMedicalComplianceList;

    private PatientAssessment patientAssessment;

    private PatientTracker patientTracker;
}

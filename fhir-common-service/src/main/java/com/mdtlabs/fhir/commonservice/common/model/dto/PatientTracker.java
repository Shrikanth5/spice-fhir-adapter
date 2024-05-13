package com.mdtlabs.fhir.commonservice.common.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * This DTO class handling the PatientTracker details
 * </p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
public class PatientTracker implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String identityType;

    private String identityValue;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private Integer age;

    private String gender;

    private String phoneNumber;

    private Double height;

    private Double weight;

    private Double bmi;

    private Boolean isRegularSmoker;

    private Long countryId;

    private Long siteId;

    private Long operatingUnitId;

    private Long accountId;

    private Long programId;

    private Integer avgSystolic;

    private Integer avgDiastolic;

    private Integer avgPulse;

    private String glucoseUnit;

    private String glucoseType;

    private Double glucoseValue;

    private String cvdRiskLevel;

    private Integer cvdRiskScore;

    private Long screeningLogId;

    private Date nextMedicalReviewDate;

    private Date nextBpAssessmentDate;

    private Date nextBgAssessmentDate;

    private Long patientId;

    private String patientStatus;

    private Boolean isObservation;

    private Boolean isScreening;

    private Boolean screeningReferral;

    private Date enrollmentAt;

    private Date lastReviewDate;

    private Date lastAssessmentDate;

    private Boolean isConfirmDiagnosis;

    private String diagnosisComments;

    private List<String> confirmDiagnosis;

    private List<String> provisionalDiagnosis;

    private Integer phq4Score;

    private boolean isInitialReview;

    private String phq4RiskLevel;

    private Integer phq4FirstScore;

    private Integer phq4SecondScore;

    private Integer phq9Score;

    private String phq9RiskLevel;

    private Integer gad7Score;

    private String gad7RiskLevel;

    private String riskLevel;

    private Boolean isPregnant;

    private boolean isRedRiskPatient;

    private boolean isLabTestReferred;

    private boolean isMedicationPrescribed;

    private Date lastMedicationPrescribedDate;

    private Boolean isHtnDiagnosis;

    private Boolean isDiabetesDiagnosis;

    private Date lastLabtestReferredDate;

    private long totalCount;

    private String deleteReason;

    private String deleteOtherReason;

    private String qrCode;

    private String village;

    private String suicidalIdeation;

    private Integer cageAid;

    private boolean isPsychologicalAssessment;

    private Boolean isPregnancyRisk;

    private Long tenantId;

    private boolean isActive;

    private boolean isDeleted;

    private Long createdBy;

    @NotNull
    private Long updatedBy;

    @NotNull
    private Date createdAt;

    @NotNull
    private Date updatedAt;
}

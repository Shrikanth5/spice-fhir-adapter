package com.mdtlabs.fhir.commonservice.common.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mdtlabs.fhir.commonservice.common.constants.ErrorConstants;
import com.mdtlabs.fhir.commonservice.common.utils.EnrollmentInfo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * This DTO class handling the BpLog details
 * </p>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BpLogDTO {

    private Long id;

    private Long tenantId;

    private Long createdBy;

    @NotNull
    private Long updatedBy;
    @NotNull
    private Date createdAt;

    @NotNull
    private Date updatedAt;

    private boolean isDeleted;

    @NotNull(message = ErrorConstants.AVG_SYSTOLIC_NOT_NULL, groups = {Default.class, EnrollmentInfo.class})
    private Integer avgSystolic;

    @NotNull(message = ErrorConstants.AVG_DIASTOLIC_NOT_NULL, groups = {Default.class, EnrollmentInfo.class})
    private Integer avgDiastolic;

    private Integer avgPulse;

    private Double height;

    private Double weight;

    private Double bmi;

    private Double temperature;

    private String cvdRiskLevel;

    private Integer cvdRiskScore;

    private boolean isLatest;

    private Boolean isRegularSmoker;

    private String type;

    private Long patientTrackId;

    private Long screeningId;

    @NotNull(message = ErrorConstants.BP_LOG_DETAILS_NOT_EMPTY, groups = {EnrollmentInfo.class})
    @Size(min = 3, message = ErrorConstants.BP_LOG_DETAILS_MIN_SIZE, groups = {EnrollmentInfo.class})
    private List<BpLogDetails> bpLogDetails;

    private String riskLevel;

    private boolean isUpdatedFromEnrollment;

    private String bpArm;

    private String bpPosition;

    private String covidVaccStatus;

    private String assessmentCategory;

    private String assessmentLandmark;

    private String notes;

    private String insuranceStatus;

    private String insuranceType;

    private String insuranceId;

    private String otherInsurance;

    private Date bpTakenOn;
    private String uuid;
    private Long bpLogId;

    private Long assessmentTenantId;

    private boolean isRedRiskPatient;

    private Boolean isBeforeHtnDiagnosis;

    private String unitMeasurement;

    private boolean isOldRecord;

    private List<PregnancySymptomsDTO> diabetes;
    //
    private String diabetesOtherSymptoms;
    private List<String> symptoms;
    private boolean isActive;

    public BpLogDTO(
            Integer avgSystolic,
            Integer avgDiastolic,
            Double bmi, String cvdRiskLevel, Integer cvdRiskScore) {
        this.avgSystolic = avgSystolic;
        this.avgDiastolic = avgDiastolic;
        this.bmi = bmi;
        this.cvdRiskLevel = cvdRiskLevel;
        this.cvdRiskScore = cvdRiskScore;
    }

    public BpLogDTO() {
    }

    public BpLogDTO(
            Integer avgSystolic,
            Integer avgDiastolic,
            Double bmi) {
        this.avgSystolic = avgSystolic;
        this.avgDiastolic = avgDiastolic;
        this.bmi = bmi;
    }

    public BpLogDTO(Long id, Integer avgSystolic, Integer avgDiastolic,
                    Integer avgPulse, Date createdAt, Date bpTakenOn) {
        super();
        this.id = id;
        this.avgSystolic = avgSystolic;
        this.avgDiastolic = avgDiastolic;
        this.avgPulse = avgPulse;
        this.createdAt = createdAt;
        this.bpTakenOn = bpTakenOn;
    }
}
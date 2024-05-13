package com.mdtlabs.fhir.commonservice.common.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * This DTO class handling the GlucoseLog details
 * </p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GlucoseLogDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private Double hba1c;
    private Long createdBy;

    @NotNull
    private Long updatedBy;
    @NotNull
    private Date createdAt;

    @NotNull
    private Date updatedAt;

    private boolean isDeleted;

    private String hba1cUnit;

    private String glucoseType;

    private Double glucoseValue;

    private Date lastMealTime;

    private Date glucoseDateTime;

    private Date hba1cDateTime;

    private String glucoseUnit;

    private String type;

    private Boolean isBeforeDiabetesDiagnosis;

    private Long glucoseId;

    private boolean isLatest;

    private Long patientTrackId;

    private boolean isUpdatedFromEnrollment;

    private Long screeningId;

    private Long assessmentTenantId;

    private Date bgTakenOn;

    private boolean isOldRecord;

    private Long tenantId;

    private List<String> symptoms;

    private Long glucoseLogId;
    private List<PregnancySymptomsDTO> diabetes;
    private String diabetesOtherSymptoms;

    private Boolean isBeforeDiabeticDiagnosis;
    private boolean isActive;
    private String uuid;
    private String unitMeasurement;

    public GlucoseLogDTO(String glucoseType, Double glucoseValue, Date lastMealTime, Date glucoseDateTime,
                         String glucoseUnit) {
        this.glucoseType = glucoseType;
        this.glucoseValue = glucoseValue;
        this.lastMealTime = lastMealTime;
        this.glucoseDateTime = glucoseDateTime;
        this.glucoseUnit = glucoseUnit;
    }

    public GlucoseLogDTO(String glucoseType, Double glucoseValue, String glucoseUnit) {
        this.glucoseType = glucoseType;
        this.glucoseValue = glucoseValue;
        this.glucoseUnit = glucoseUnit;
    }

    public GlucoseLogDTO() {
    }

    public GlucoseLogDTO(Long id, Double hba1c, String hba1cUnit, String glucoseType, Double glucoseValue,
                         Date glucoseDateTime, Date hba1cDateTime, String glucoseUnit, Date createdAt) {
        this.id = id;
        this.hba1c = hba1c;
        this.hba1cUnit = hba1cUnit;
        this.glucoseType = glucoseType;
        this.glucoseValue = glucoseValue;
        this.glucoseDateTime = glucoseDateTime;
        this.hba1cDateTime = hba1cDateTime;
        this.glucoseUnit = glucoseUnit;
        this.createdAt = createdAt;
    }

}
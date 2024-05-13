package com.mdtlabs.fhir.commonservice.common.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * This class is an Entity for manipulation patient pregnancy details.
 * </p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
public class PatientPregnancyDetails implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer pregnancyFetusesNumber;

    private Integer gravida;

    private Integer parity;

    private Double temperature;

    private Date lastMenstrualPeriodDate;

    private Date estimatedDeliveryDate;

    private Boolean isOnTreatment;

    private Date diagnosisTime;

    private List<String> diagnosis;

    private String neonatalOutcomes;

    private String maternalOutcomes;

    private Boolean attendedAncClinic;

    private Date actualDeliveryDate;

    private Boolean isInterestedToEnroll;

    private Boolean isIptDrugProvided;

    private Boolean isIronFolateProvided;

    private Boolean isMosquitoNetProvided;

    private Boolean isLatest;

    private Boolean isInitialReview;

    private Boolean isDangerSymptom;

    private Long patientTrackId;

    private List<String> confirmDiagnosis;

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

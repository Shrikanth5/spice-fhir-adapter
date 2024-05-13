package com.mdtlabs.fhir.commonservice.common.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * This DTO class handling the PatientMedicalCompliance details
 * </p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
public class PatientMedicalCompliance implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String otherCompliance;

    private Long patientTrackId;

    private Long complianceId;

    private Long bpLogId;

    private Long assessmentLogId;

    private Long assessmentTenantId;

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

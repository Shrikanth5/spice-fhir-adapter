package com.mdtlabs.fhir.commonservice.common.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * This DTO class handling the FhirEnrollmentRequest details
 * </p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
public class FhirEnrollmentRequestDto {

    private String type;

    private Long patientTrackId;

    private PatientDTO patient;

    private String village;

    private BpLogDTO bpLog;

    private Long spiceBpLogId;

    private GlucoseLogDTO glucoseLog;

    private Long spiceGlucoseLogId;

    @NotNull
    private Long createdBy;
    @NotNull
    private Long updatedBy;
    @NotNull
    private Date createdAt;

    @NotNull
    private Date updatedAt;

}

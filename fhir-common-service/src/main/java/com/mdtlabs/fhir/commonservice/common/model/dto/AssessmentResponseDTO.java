package com.mdtlabs.fhir.commonservice.common.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * This DTO class handling the AssessmentResponse details
 * </p>
 */
@Data
public class AssessmentResponseDTO {
    private String glucoseLog;
    private Long patientTrackId;
    @NotNull
    @Valid
    private String bpLog;
    @NotNull
    private Long createdBy;
    @NotNull
    private Long updatedBy;
    @NotNull
    private Date createdAt;
    @NotNull
    private Date updatedAt;
}

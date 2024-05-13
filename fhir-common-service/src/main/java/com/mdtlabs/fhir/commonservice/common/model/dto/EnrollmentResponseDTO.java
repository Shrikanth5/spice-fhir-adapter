package com.mdtlabs.fhir.commonservice.common.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * This DTO class handling the EnrollmentResponse details
 * </p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
public class EnrollmentResponseDTO {
    private String glucoseLog;
    @NotNull
    @Valid
    private String bpLog;
    private String patient;
    @NotNull
    private Long createdBy;
    @NotNull
    private Long updatedBy;
    @NotNull
    private Date createdAt;
    @NotNull
    private Date updatedAt;
}

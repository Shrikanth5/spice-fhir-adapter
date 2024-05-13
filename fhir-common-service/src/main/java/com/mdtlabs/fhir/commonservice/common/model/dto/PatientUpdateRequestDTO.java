package com.mdtlabs.fhir.commonservice.common.model.dto;

import lombok.Data;

/**
 * <p>
 * The {@code PatientUpdateRequestDTO} class represents a Data Transfer Object for the Patient entity.
 * </p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
public class PatientUpdateRequestDTO {
    private String type;
    private PatientDTO patientDTO;
    private PatientTracker patientTracker;
    private Long createdBy;
    private Long updatedBy;
}

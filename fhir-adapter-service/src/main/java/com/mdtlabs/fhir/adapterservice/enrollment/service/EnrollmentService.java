package com.mdtlabs.fhir.adapterservice.enrollment.service;

import com.mdtlabs.fhir.commonservice.common.model.dto.EnrollmentResponseDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirEnrollmentRequestDto;
import com.mdtlabs.fhir.commonservice.common.model.dto.PatientUpdateRequestDTO;
import org.hl7.fhir.instance.model.api.IBaseResource;

/**
 * An interface representing a service for managing enrollment patient-related operations. This interface defines
 * methods for creating FHIR patients and observation.
 * <p>
 * Author: Charan
 * <p>
 * Created on: February 27, 2024
 */
public interface EnrollmentService {

    /**
     * Creates a new FHIR patient and observation based on the provided EnrollmentRequestDTO.
     *
     * @param enrollmentRequestDTO The {@link FhirEnrollmentRequestDto} containing information for creating FHIR patient
     *                             and observation.
     * @return The resulting {@link EnrollmentResponseDTO} after processing the request.
     */
    EnrollmentResponseDTO createFhirPatient(FhirEnrollmentRequestDto enrollmentRequestDTO);

    /**
     * Creates a new FHIR patient and observation based on the provided EnrollmentRequestDTO.
     *
     * @param requestDTO The {@link PatientUpdateRequestDTO} containing information for updating FHIR patient.
     * @return The resulting {@link IBaseResource} after processing the request.
     */
    IBaseResource updatePatientDetails(PatientUpdateRequestDTO requestDTO);
}

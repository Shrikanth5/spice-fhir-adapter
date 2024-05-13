package com.mdtlabs.fhir.adapterservice.assessment.service;

import com.mdtlabs.fhir.commonservice.common.model.dto.AssessmentResponseDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirAssessmentRequestDto;

/**
 * <p>
 * Service interface for handling assessments and creating FHIR observations.
 * </p>
 * <p>
 * Author: Shrikanth
 * <p>
 * Created on: February 27, 2024
 */
public interface AssessmentService {

    /**
     * <p>
     * Creates a FHIR Observation based on the provided assessment request and updates patient details.
     * </p>
     *
     * @param assessmentRequestDTO The FHIR assessment request DTO.
     * @param spicePatientTrackId  The Spice patient track ID.
     * @return The resulting AssessmentResponseDTO.
     */
    AssessmentResponseDTO createFhirObservation(FhirAssessmentRequestDto assessmentRequestDTO,
                                                Long spicePatientTrackId);

}

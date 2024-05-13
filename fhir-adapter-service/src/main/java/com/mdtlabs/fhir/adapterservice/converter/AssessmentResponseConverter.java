package com.mdtlabs.fhir.adapterservice.converter;

import ca.uhn.fhir.context.FhirContext;
import com.mdtlabs.fhir.commonservice.common.constants.FhirConstants;
import com.mdtlabs.fhir.commonservice.common.model.dto.AssessmentResponseDTO;
import org.hl7.fhir.r4.model.Observation;
import org.springframework.stereotype.Component;

import java.util.Objects;


/**
 * <p>
 * Converts FHIR Bundle containing Observations to AssessmentResponseDTO.
 * This class provides a static method to convert a FHIR Observation resource to an AssessmentResponseDTO.
 * </p>
 * <p>
 * Author: Charan
 * <p>
 * Created on: February 27, 2024
 */
@Component
public class AssessmentResponseConverter {
    private final FhirContext fhirContext;

    public AssessmentResponseConverter(FhirContext fhirContext) {
        this.fhirContext = fhirContext;
    }

    /**
     * <p>
     * Converts a FHIR Observation resource to an AssessmentResponseDTO.
     * </p>
     *
     * @param resource The FHIR Observation resource to be converted.
     * @return The converted AssessmentResponseDTO.
     */
    public AssessmentResponseDTO convertObservationToAssessmentResponseDTO(
            Observation resource, AssessmentResponseDTO assessmentResponseDTO) {

        if (resource.hasCode()) {
            if (Objects.equals(resource.getCode().getText(), FhirConstants.BLOOD_PRESSURE)) {
                assessmentResponseDTO.setBpLog(fhirContext.newJsonParser().encodeResourceToString(resource));
            } else if (Objects.equals(resource.getCode().getText(), FhirConstants.BLOOD_GLUCOSE)) {
                assessmentResponseDTO.setGlucoseLog(fhirContext.newJsonParser().encodeResourceToString(resource));
            }
        }
        return assessmentResponseDTO;
    }

}
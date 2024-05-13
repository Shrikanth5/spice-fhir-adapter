package com.mdtlabs.fhir.adapterservice.converter;

import ca.uhn.fhir.context.FhirContext;
import com.mdtlabs.fhir.commonservice.common.constants.FhirConstants;
import com.mdtlabs.fhir.commonservice.common.model.dto.EnrollmentResponseDTO;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Converts FHIR Patient and Observation resources to an EnrollmentResponseDTO.
 * <p>
 * This converter utilizes the HAPI FHIR library.
 * </p>
 * <p>
 * Author: Charan
 * <p>
 * Created on: February 27, 2024
 */
@Component
public class EnrollmentResponseConverter {
    private final FhirContext fhirContext;

    public EnrollmentResponseConverter(FhirContext fhirContext) {
        this.fhirContext = fhirContext;
    }

    /**
     * Converts a FHIR Patient resource to an EnrollmentResponseDTO.
     *
     * @param resource The FHIR Patient resource to convert.
     * @return The resulting EnrollmentResponseDTO.
     */
    public EnrollmentResponseDTO convertPatientToEnrollmentResponseDTO(Patient resource,
                                                                       EnrollmentResponseDTO enrollmentResponseDTO) {
        enrollmentResponseDTO.setPatient(fhirContext.newJsonParser().encodeResourceToString(resource));
        return enrollmentResponseDTO;
    }

    /**
     * Converts a FHIR Observation resource to an EnrollmentResponseDTO.
     *
     * @param resource The FHIR Observation resource to convert.
     * @return The resulting EnrollmentResponseDTO.
     */
    public EnrollmentResponseDTO convertObservationToEnrollmentResponseDTO(Observation resource,
                                                                           EnrollmentResponseDTO enrollmentResponseDTO) {

        if (resource.hasCode()) {

            if (Objects.equals(resource.getCode().getText(), FhirConstants.BLOOD_PRESSURE)) {
                enrollmentResponseDTO.setBpLog(fhirContext.newJsonParser().encodeResourceToString(resource));
            } else if (Objects.equals(resource.getCode().getText(), FhirConstants.BLOOD_GLUCOSE)) {
                enrollmentResponseDTO.setGlucoseLog(fhirContext.newJsonParser().encodeResourceToString(resource));
            }
        }
        return enrollmentResponseDTO;
    }
}

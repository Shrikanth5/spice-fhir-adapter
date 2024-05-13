package com.mdtlabs.fhir.adapterservice.converter;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.constants.FhirConstants;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirAssessmentRequestDto;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Observation;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Converts AssessmentRequestDTO to FHIR Bundle containing Observations.
 * </p>
 * <p>
 * Author: Charan
 * <p>
 * Created on: February 27, 2024
 */
@Component
public class AssessmentRequestConverter {
    private final BloodPressureConverter bloodPressureConverter;
    private final BloodGlucoseConverter bloodGlucoseConverter;

    public AssessmentRequestConverter(BloodPressureConverter bloodPressureConverter,
                                      BloodGlucoseConverter bloodGlucoseConverter) {
        this.bloodPressureConverter = bloodPressureConverter;
        this.bloodGlucoseConverter = bloodGlucoseConverter;
    }

    /**
     * <p>
     * Creates a FHIR Bundle containing Observations based on the provided assessment request DTO.
     * </p>
     *
     * @param assessmentRequestDTO    The FHIR assessment request DTO.
     * @param fhirPatientId           The FHIR patient ID.
     * @param updatedByPractitionerId The ID of the practitioner who updated the assessment.
     * @return The created FHIR Bundle.
     */
    public Bundle createFhirBundle(FhirAssessmentRequestDto assessmentRequestDTO, String fhirPatientId, Long updatedByPractitionerId) {
        Observation bpLogObservation = bloodPressureConverter
                .convertBloodPressureObservationToFhirBundleEntity(assessmentRequestDTO.getBpLog(), fhirPatientId,
                        assessmentRequestDTO.getType(), updatedByPractitionerId,
                        assessmentRequestDTO.getPatientTracker().getIsPregnant(), assessmentRequestDTO.getPatientTracker().getCountryId());
        Logger.logInfo(Constants.CONVERTED_SPICE_BP_TO_FHIR_OBSERVATION);

        Bundle bundle = new Bundle().setType(Bundle.BundleType.TRANSACTION);
        bundle.addEntry().setResource(bpLogObservation).getRequest().setMethod(Bundle.HTTPVerb.POST)
                .setUrl(FhirConstants.OBSERVATION);
        if (null != assessmentRequestDTO.getGlucoseLog() && null != assessmentRequestDTO.getGlucoseLog().getId()) {
            Observation glucoseLogObservation = bloodGlucoseConverter
                    .convertBloodGlucoseObservationToFhirBundleEntity(assessmentRequestDTO.getGlucoseLog(),
                            fhirPatientId, updatedByPractitionerId, assessmentRequestDTO.getPatientTracker().getCountryId());
            Logger.logInfo(Constants.CONVERTED_SPICE_BG_TO_FHIR_OBSERVATION);
            bundle.addEntry().setResource(glucoseLogObservation).getRequest().setMethod(Bundle.HTTPVerb.POST)
                    .setUrl(FhirConstants.OBSERVATION);
        }
        Logger.logInfo(Constants.CONVERTED_ASSESSMENT_MODULE_TO_FHIR_BUNDLE, bundle);
        return bundle;

    }

}

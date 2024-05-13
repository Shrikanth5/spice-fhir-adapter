package com.mdtlabs.fhir.adapterservice.converter;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.constants.FhirConstants;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirEnrollmentRequestDto;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Converts FhirEnrollmentRequestDto to a FHIR Bundle containing Patient and Observation resources.
 * </p>
 * <p>
 * Author: Charan
 * <p>
 * Created on: February 27, 2024
 */
@Component
public class EnrollmentRequestConverter {
    private final PatientConverter patientResourceConverter;
    private final BloodPressureConverter bloodPressureConverter;
    private final BloodGlucoseConverter bloodGlucoseConverter;

    public EnrollmentRequestConverter(PatientConverter patientResourceConverter,
                                      BloodPressureConverter bloodPressureConverter,
                                      BloodGlucoseConverter bloodGlucoseConverter) {
        this.patientResourceConverter = patientResourceConverter;
        this.bloodPressureConverter = bloodPressureConverter;
        this.bloodGlucoseConverter = bloodGlucoseConverter;
    }

    /**
     * Creates a FHIR Bundle containing Patient and Observation resources based on the provided enrollment request DTO.
     *
     * @param enrollmentRequestDTO    The FHIR enrollment request DTO.
     * @param fhirPatientId           The FHIR patient ID.
     * @param createdByPractitionerId The ID of the practitioner who created the resources.
     * @param updatedByPractitionerId The ID of the practitioner who updated the resources.
     * @param organizationId          The OrganizationId mapped for siteID
     * @return The created FHIR Bundle.
     */
    public Bundle createFhirBundle(FhirEnrollmentRequestDto enrollmentRequestDTO, String fhirPatientId,
                                   Long createdByPractitionerId, Long updatedByPractitionerId,
                                   Long organizationId) {
        Patient patient = patientResourceConverter.
                convertPatientToFhirBundleEntity(enrollmentRequestDTO.getPatient(), updatedByPractitionerId, organizationId);
        Logger.logInfo(Constants.CONVERTED_SPICE_ENROLLED_PATIENT_TO_FHIR_PATIENT);
        Observation bpLogObservation = bloodPressureConverter
                .convertBloodPressureObservationToFhirBundleEntity(enrollmentRequestDTO.getBpLog(), fhirPatientId,
                        enrollmentRequestDTO.getType(), updatedByPractitionerId,
                        enrollmentRequestDTO.getPatient().getIsPregnant(), enrollmentRequestDTO.getPatient().getCountryId());
        Logger.logInfo(Constants.CONVERTED_SPICE_BP_TO_FHIR_OBSERVATION);

        Bundle bundle = new Bundle().setType(Bundle.BundleType.TRANSACTION);
        bundle.addEntry().setFullUrl(FhirConstants.PATIENT_FULL_URL)
                .setResource(patient).getRequest().setMethod(Bundle.HTTPVerb.POST).setUrl(FhirConstants.PATIENT);
        bundle.addEntry().setResource(bpLogObservation).getRequest().setMethod(Bundle.HTTPVerb.POST)
                .setUrl(FhirConstants.OBSERVATION);
        Logger.logInfo(Constants.CONVERTED_SPICE_BG_TO_FHIR_OBSERVATION);

        if (null != enrollmentRequestDTO.getGlucoseLog().getId()) {
            Observation glucoseLogObservation = bloodGlucoseConverter
                    .convertBloodGlucoseObservationToFhirBundleEntity(enrollmentRequestDTO.getGlucoseLog(),
                            fhirPatientId, updatedByPractitionerId, enrollmentRequestDTO.getPatient().getCountryId());
            bundle.addEntry().setResource(glucoseLogObservation).getRequest().setMethod(Bundle.HTTPVerb.POST)
                    .setUrl(FhirConstants.OBSERVATION);
        }
        Logger.logInfo(Constants.CONVERTED_ENROLLMENT_MODULE_TO_FHIR_BUNDLE, bundle);
        return bundle;
    }
}

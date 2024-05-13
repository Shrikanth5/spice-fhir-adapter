package com.mdtlabs.fhir.adapterservice.converter;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.constants.FhirConstants;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotFoundException;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirEnrollmentRequestDto;
import com.mdtlabs.fhir.commonservice.common.model.dto.PatientDTO;
import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.Enumerations;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Reference;
import org.springframework.stereotype.Component;

/**
 * Converts a {@link FhirEnrollmentRequestDto} to a FHIR Patient resource.
 * <p>
 * This converter creates a FHIR Patient resource based on the information provided in the enrollment request DTO.
 * </p>
 * <p>
 * Author: Charan
 * <p>
 * Created on: February 27, 2024
 */
@Component
public class PatientConverter {

    /**
     * Converts a {@link FhirEnrollmentRequestDto} to a FHIR Patient resource.
     *
     * @param patientDTO              The enrollment request DTO containing patient information.
     * @param updatedByPractitionerId The ID of the practitioner who updated the patient record.
     * @param organizationId          The Organization ID from mapping SiteID
     * @return The resulting FHIR Patient resource.
     */
    public Patient convertPatientToFhirBundleEntity(PatientDTO patientDTO,
                                                    Long updatedByPractitionerId,
                                                    Long organizationId) {
        Patient patient = new Patient();
        patient.setActive(patientDTO.isActive());
        patient.addIdentifier().setSystem(FhirConstants.IDENTIFIER_URL + FhirConstants.PATIENT).
                setValue(String.valueOf(patientDTO.getIdentityValue()));
        if (patientDTO.getClientRegistryNumber() != null) {
            patient.addIdentifier()
                    .setSystem(FhirConstants.CLIENT_REGISTRY_IDENTIFIER_URL)
                    .setValue(patientDTO.getClientRegistryNumber());
        }
        Reference organizationReference =
                new Reference(FhirConstants.ORGANIZATION + Constants.FORWARD_SLASH +
                        organizationId);
        patient.setManagingOrganization(organizationReference);
        patient.setBirthDate(patientDTO.getDateOfBirth());
        String genderCode = patientDTO.getGender().equals(Constants.NON_BINARY)
                ? Constants.OTHER : patientDTO.getGender().toLowerCase();
        patient.setGender(Enumerations.AdministrativeGender.fromCode(genderCode));
        //set the name field
        HumanName name = new HumanName();
        name.addGiven(patientDTO.getFirstName());
        if (null != patientDTO.getMiddleName()) {
            name.addGiven(patientDTO.getMiddleName());
        }
        if (null != patientDTO.getInitial()) {
            name.addPrefix(patientDTO.getInitial());
        }
        name.setFamily(patientDTO.getLastName());
        patient.addName(name);
        
        ContactPoint phone = new ContactPoint();
        phone.setSystem(ContactPoint.ContactPointSystem.PHONE);
        phone.setValue(patientDTO.getPhoneNumber());
        patient.addTelecom(phone);

        if (null != updatedByPractitionerId) {
            
            Reference practitionerReference =
                    new Reference(FhirConstants.PRACTITIONER + Constants.FORWARD_SLASH +
                            updatedByPractitionerId);
            patient.addGeneralPractitioner(practitionerReference);
        } else {
            Logger.logError(String.valueOf(1005));
            throw new DataNotFoundException(1005);
        }
        return patient;
    }
}

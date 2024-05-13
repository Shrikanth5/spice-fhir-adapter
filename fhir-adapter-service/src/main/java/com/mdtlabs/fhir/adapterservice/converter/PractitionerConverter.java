package com.mdtlabs.fhir.adapterservice.converter;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.constants.FhirConstants;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirUserDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.PractitionerDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.PractitionerRoleDTO;
import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.Enumerations;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Practitioner;
import org.hl7.fhir.r4.model.PractitionerRole;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * Component responsible for converting between FHIR entities (Practitioner, PractitionerRole) and corresponding Data
 * Transfer Objects (DTOs) like PractitionerDTO and PractitionerRoleDTO.
 * </p>
 * <p>
 * Author: Charan
 * <p>
 * Created on: February 27, 2024
 */
@Component
public class PractitionerConverter {

    /**
     * Converts a FhirUserRequestDto to a Practitioner FHIR entity.
     *
     * @param userDTO The FHIR user request DTO.
     * @return The converted Practitioner FHIR entity.
     */
    public Practitioner convertUserToFhirEntity(FhirUserDTO userDTO) {
        Practitioner practitioner = new Practitioner();
        practitioner.addIdentifier().setSystem(FhirConstants.IDENTIFIER_URL + FhirConstants.PRACTITIONER).
                setValue(String.valueOf(userDTO.getId()));

        practitioner.setActive(userDTO.isActive());
        //set the name field
        HumanName name = new HumanName();
        name.addGiven(userDTO.getFirstName());
        name.setFamily(userDTO.getLastName());
        practitioner.addName(name);

        if (null != userDTO.getGender()) {
            practitioner.setGender(
                    (userDTO.getGender().equals(Constants.NON_BINARY)
                    ? Enumerations.AdministrativeGender.OTHER
                    : Enumerations.AdministrativeGender.fromCode(userDTO.getGender().toLowerCase())));
        }

        
        ContactPoint phone = new ContactPoint();
        phone.setSystem(ContactPoint.ContactPointSystem.PHONE);
        phone.setValue(userDTO.getPhoneNumber());
        practitioner.addTelecom(phone);

        
        ContactPoint email = new ContactPoint();
        email.setSystem(ContactPoint.ContactPointSystem.EMAIL);
        email.setValue(userDTO.getUsername());
        practitioner.addTelecom(email);
        Logger.logInfo(Constants.USER_SUCCESSFULLY_CONVERTED);
        return practitioner;

    }

    /**
     * Converts a Practitioner FHIR entity to a PractitionerDTO.
     *
     * @param practitioner The Practitioner FHIR entity.
     * @return The converted PractitionerDTO.
     */
    public PractitionerDTO convertPractitionerToUserDto(Practitioner practitioner) {
        PractitionerDTO practitionerDTO = new PractitionerDTO();

        
        practitionerDTO.setFhirId((long) Integer.parseInt(practitioner.getIdElement().getIdPart()));

        practitionerDTO.setId(Long.valueOf(practitioner.getIdentifierFirstRep().getValue()));

        practitionerDTO.setActive(practitioner.getActive());

        if (null != practitioner.getNameFirstRep().getGiven().get(0).getValue()) {
            practitionerDTO.setFirstName(practitioner.getNameFirstRep().getGiven().get(0).getValue());
        }

        if (null != practitioner.getNameFirstRep().getFamily()) {
            practitionerDTO.setLastName(practitioner.getNameFirstRep().getFamily());
        }

        practitionerDTO.setPhoneNumber(practitioner.getTelecomFirstRep().getValue());

        
        List<ContactPoint> contactPoints = practitioner.getTelecom();

        
        contactPoints.stream()
                .filter(cp -> cp.hasSystem() && cp.getSystem().equals(ContactPoint.ContactPointSystem.PHONE))
                .map(ContactPoint::getValue)
                .forEach(practitionerDTO::setPhoneNumber);

        
        contactPoints.stream()
                .filter(cp -> cp.hasSystem() && cp.getSystem().equals(ContactPoint.ContactPointSystem.EMAIL))
                .map(ContactPoint::getValue)
                .forEach(practitionerDTO::setUsername);

        practitionerDTO.setGender(practitioner.getGender().toString());
        Logger.logInfo(Constants.PRACTITIONER_CONVERTED_TO_USER);
        return practitionerDTO;
    }


    /**
     * Converts a PractitionerRoleDTO to a PractitionerRole FHIR entity.
     *
     * @param practitionerRoleDTO The PractitionerRoleDTO.
     * @return The converted PractitionerRole FHIR entity.
     */
    public PractitionerRole convertUserRoleToFhirEntity(PractitionerRoleDTO practitionerRoleDTO) {
        PractitionerRole practitionerRole = new PractitionerRole();

        practitionerRole.addIdentifier().setSystem(FhirConstants.IDENTIFIER_URL + FhirConstants.PRACTITIONER_ROLE).
                setValue(String.valueOf(practitionerRoleDTO.getId()));

        practitionerRole.setActive(practitionerRoleDTO.isActive());
        practitionerRole.addCode().addCoding()
                .setSystem(FhirConstants.FHIR_ROLE_URL)
                .setCode(FhirConstants.DOCTOR)
                .setDisplay(FhirConstants.DOCTOR);

        Logger.logInfo(Constants.USER_ROLE_CONVERTED_TO_PRACTITIONER);
        return practitionerRole;
    }

    /**
     * Converts a PractitionerRole FHIR entity to a PractitionerRoleDTO.
     *
     * @param practitionerRole The PractitionerRole FHIR entity.
     * @return The converted PractitionerRoleDTO.
     */
    public PractitionerRoleDTO convertPractitionerRoleToRoleDto(PractitionerRole practitionerRole) {
        PractitionerRoleDTO practitionerRoleDTO = new PractitionerRoleDTO();
        
        practitionerRoleDTO.setFhirRoleId(Integer.parseInt(practitionerRole.getIdElement().getIdPart()));

        practitionerRoleDTO.setId(Long.parseLong(practitionerRole.getIdentifierFirstRep().getValue()));

        practitionerRoleDTO.setActive(practitionerRole.getActive());

        Logger.logInfo(Constants.PRACTITIONER_ROLE_CONVERTED_TO_USER_ROLE);
        return practitionerRoleDTO;
    }
}
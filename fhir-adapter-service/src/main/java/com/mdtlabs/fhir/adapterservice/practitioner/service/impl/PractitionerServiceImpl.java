package com.mdtlabs.fhir.adapterservice.practitioner.service.impl;

import ca.uhn.fhir.rest.client.exceptions.FhirClientConnectionException;
import com.mdtlabs.fhir.adapterservice.converter.PractitionerConverter;
import com.mdtlabs.fhir.adapterservice.fhirclient.FhirClient;
import com.mdtlabs.fhir.adapterservice.practitioner.service.PractitionerService;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.exception.RequestTimeOutException;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirUserDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.PractitionerDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.PractitionerRoleDTO;
import com.mdtlabs.fhir.commonservice.common.model.entity.UserSpiceFhirMapping;
import com.mdtlabs.fhir.commonservice.common.repository.UserSpiceFhirMappingRepository;
import org.hl7.fhir.r4.model.Practitioner;
import org.hl7.fhir.r4.model.PractitionerRole;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing users and roles in FHIR.
 * <p>
 * This class provides methods to create, retrieve, and manage FHIR practitioners and roles.
 * </p>
 * <p>
 * Author: Charan
 * <p>
 * Created on: February 27, 2024
 */
@Service
public class PractitionerServiceImpl implements PractitionerService {

    private final FhirClient fhirClient;

    private final PractitionerConverter practitionerConverter;

    private final UserSpiceFhirMappingRepository userSpiceFhirMappingRepository;

    public PractitionerServiceImpl(FhirClient fhirClient,
                                   PractitionerConverter practitionerConverter,
                                   UserSpiceFhirMappingRepository userSpiceFhirMappingRepository) {
        this.fhirClient = fhirClient;
        this.practitionerConverter = practitionerConverter;
        this.userSpiceFhirMappingRepository = userSpiceFhirMappingRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PractitionerDTO createAndMapFhirPractitioner(FhirUserDTO userDTO) {
        Practitioner practitioner = practitionerConverter.convertUserToFhirEntity(userDTO);
        PractitionerDTO userDetails = practitionerConverter.convertPractitionerToUserDto(createPractitioner(practitioner));
        userSpiceFhirMappingRepository.save(new UserSpiceFhirMapping(userDetails.getId(),
                userDetails.getFhirId(), true, false));
        Logger.logInfo(Constants.USER_PRACTITIONER_MAPPING_SAVED_SUCCESSFULLY);
        return userDetails;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PractitionerRoleDTO createAndMapFhirPractitionerRole(PractitionerRoleDTO roleDetails) {
        PractitionerRole practitionerRole = practitionerConverter.convertUserRoleToFhirEntity(roleDetails);

        return practitionerConverter.convertPractitionerRoleToRoleDto(createPractitionerRole(practitionerRole));
    }

    /**
     * Creates a new FHIR practitioner.
     *
     * @param practitioner The practitioner to be created.
     * @return The created Practitioner.
     */
    public Practitioner createPractitioner(Practitioner practitioner) {
        try {
            return fhirClient.sendResourceToFHIR(practitioner);
        } catch (FhirClientConnectionException timeoutException) {
            throw new RequestTimeOutException(20010, timeoutException.getMessage());
        }
    }

    /**
     * Creates a new FHIR practitioner role.
     *
     * @param practitionerRole The practitioner role to be created.
     * @return The created PractitionerRole.
     */
    public PractitionerRole createPractitionerRole(PractitionerRole practitionerRole) {
        try {
            Logger.logInfo(Constants.PRACTITIONER_ROLE_LOG, practitionerRole);
            return fhirClient.sendResourceToFHIR(practitionerRole);
        } catch (FhirClientConnectionException timeoutException) {
            throw new RequestTimeOutException(20010, timeoutException.getMessage());
        }
    }
}

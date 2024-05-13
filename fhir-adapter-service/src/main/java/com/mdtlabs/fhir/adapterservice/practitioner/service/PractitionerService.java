package com.mdtlabs.fhir.adapterservice.practitioner.service;

import com.mdtlabs.fhir.commonservice.common.model.dto.FhirUserDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.PractitionerDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.PractitionerRoleDTO;

/**
 * Service interface for managing users and roles in FHIR.
 * <p>
 * Author: Charan
 * <p>
 * Created on: February 27, 2024
 */
public interface PractitionerService {

    /**
     * Creates a new FHIR Practitioner based on the provided user data.
     *
     * @param userDTO The PractitionerDTO containing the data for creating the FHIR Practitioner.
     * @return A PractitionerDTO representing the created FHIR Practitioner.
     */
    PractitionerDTO createAndMapFhirPractitioner(FhirUserDTO userDTO);

    /**
     * Creates a new FHIR Practitioner Role based on the provided role details.
     *
     * @param roleDetails The PractitionerRoleDTO containing the data for creating the FHIR Practitioner Role.
     * @return A PractitionerRoleDTO representing the created FHIR Practitioner Role.
     */
    PractitionerRoleDTO createAndMapFhirPractitionerRole(PractitionerRoleDTO roleDetails);

}

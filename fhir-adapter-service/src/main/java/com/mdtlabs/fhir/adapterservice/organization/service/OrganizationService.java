package com.mdtlabs.fhir.adapterservice.organization.service;

import com.mdtlabs.fhir.commonservice.common.model.dto.FhirOrganizationRequestDto;
import org.hl7.fhir.r4.model.Organization;

/**
 * Service interface for managing users and roles in FHIR.
 * <p>
 * Author: Akash Gopinath
 * <p>
 * Created on: April 09, 2024
 */
public interface OrganizationService {


    /**
     * Create and map fhir organization.
     *
     * @param fhirOrganizationRequestDto the fhir organization request dto
     */
    void createAndMapFhirOrganization(FhirOrganizationRequestDto fhirOrganizationRequestDto);

    /**
     * Send organizations to fhir server string.
     *
     * @param organization the organization
     * @return the string
     */
    String sendOrganizationsToFhirServer(Organization organization);

}

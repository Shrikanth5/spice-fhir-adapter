package com.mdtlabs.fhir.adapterservice.migration.service;

import org.hl7.fhir.r4.model.Organization;

/**
 * The interface Organization service.
 * Author: Akash Gopinath
 * Created on: April 10, 2024
 */
public interface FhirOrganizationService {
    /**
     * Send organizations to fhir server string.
     *
     * @param organization the organization
     * @return the string
     */
    String sendOrganizationsToFhirServer(Organization organization);
}

package com.mdtlabs.fhir.adapterservice.migration.service;

import org.hl7.fhir.r4.model.Practitioner;

/**
 * The interface Practitioner service.
 * Author: Akash Gopinath
 * Created on: April 10, 2024
 */
public interface FhirPractitionerService {

    /**
     * Save practitioner to hapi fhir server string.
     *
     * @param userId       the user id
     * @param practitioner the practitioner
     * @return the string
     */
    String savePractitionerToHapiFhirServer(Long userId, Practitioner practitioner);
}

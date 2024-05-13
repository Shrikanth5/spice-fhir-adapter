package com.mdtlabs.fhir.adapterservice.migration.service;

import java.util.List;

import com.mdtlabs.fhir.adapterservice.migration.model.SpiceUser;

/**
 * The interface Fhir user service.
 * Author: Akash Gopinath
 * Created on: April 10, 2024
 */
public interface FhirUserService {

    /**
     * Insert spice users.
     *
     * @param spiceUser      the spice user
     * @param practitionerId the practitioner id
     */
    void insertSpiceUsers(SpiceUser spiceUser, Long practitionerId);

    /**
     * Insert into spice fhir mapping.
     *
     * @param siteId         the site id
     * @param organisationId the organisation id
     */
    void insertIntoSpiceFhirMapping(Long siteId, String organisationId);

    /**
     * Retrieve the userIds from spice fhir mapping.
     *
     * @return List<Long> list of userIds.
     */
    List<Long> findAllUserIds();

    /**
     * Retrieve the siteIds from spice fhir mapping.
     *
     * @return List<Long> list of siteIds.
     */
    List<Long> findAllSitesIds();
}

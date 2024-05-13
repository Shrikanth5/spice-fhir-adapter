package com.mdtlabs.fhir.adapterservice.migration.service.impl;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import com.mdtlabs.fhir.adapterservice.migration.model.SpiceUser;
import com.mdtlabs.fhir.adapterservice.migration.repository.FhirSiteMigrationRepository;
import com.mdtlabs.fhir.adapterservice.migration.repository.FhirUserMigrationRepository;
import com.mdtlabs.fhir.adapterservice.migration.service.FhirUserService;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;
import com.mdtlabs.fhir.commonservice.common.model.entity.SiteFhirMapping;
import com.mdtlabs.fhir.commonservice.common.model.entity.UserSpiceFhirMapping;


/**
 * The type Fhir user service.
 * Author: Akash Gopinath
 * Created on: April 10, 2024
 */
@Service
public class FhirUserServiceImpl implements FhirUserService {

    private final FhirUserMigrationRepository fhirUserMigrationRepository;
    private final ObjectMapper mapper;
    private final FhirSiteMigrationRepository fhirSiteMigrationRepository;


    /**
     * Instantiates a new Fhir user service.
     *
     * @param fhirUserMigrationRepository the fhir user migration repository
     * @param mapper                      the mapper
     */
    public FhirUserServiceImpl(FhirUserMigrationRepository fhirUserMigrationRepository,
                               ObjectMapper mapper, FhirSiteMigrationRepository fhirSiteMigrationRepository) {
        this.fhirUserMigrationRepository = fhirUserMigrationRepository;
        this.mapper = mapper;
        this.fhirSiteMigrationRepository = fhirSiteMigrationRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void  insertSpiceUsers(SpiceUser spiceUser, Long practitionerId) {
        UserSpiceFhirMapping user = fhirUserMigrationRepository.save(new UserSpiceFhirMapping(spiceUser.getId(),
                practitionerId, true, false));
        Logger.logInfo("Saved User Details : ", user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertIntoSpiceFhirMapping(Long siteId, String organisationId) {
        fhirSiteMigrationRepository.save(new SiteFhirMapping(siteId,
                Long.valueOf(organisationId), true, false));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Long> findAllUserIds() {
        return fhirUserMigrationRepository.getAllSpiceUserIds();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Long> findAllSitesIds() {
        return fhirSiteMigrationRepository.getAllSpiceSiteIds();
    }

}

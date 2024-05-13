package com.mdtlabs.fhir.adapterservice.migration.service.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import com.mdtlabs.fhir.adapterservice.migration.model.SpiceUser;
import com.mdtlabs.fhir.adapterservice.migration.repository.SpiceUserMigrationRepository;
import com.mdtlabs.fhir.adapterservice.migration.service.SpiceUserService;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;

/**
 * The type Spice user service.
 * Author: Akash Gopinath
 * Created on: April 10, 2024
 */
@Service
public class SpiceUserServiceImpl implements SpiceUserService {

    private final SpiceUserMigrationRepository userMigrationRepository;
    private final ObjectMapper mapper;

    /**
     * Instantiates a new Spice user service.
     *
     * @param userMigrationRepository the user migration repository
     * @param mapper                  the mapper
     */
    public SpiceUserServiceImpl(SpiceUserMigrationRepository userMigrationRepository, ObjectMapper mapper) {
        this.userMigrationRepository = userMigrationRepository;
        this.mapper = mapper;
    }

    @Override
    public List<SpiceUser> getAllSpiceUsers() {
        List<Map<String, Object>> allSpiceUsers = userMigrationRepository.findAllUsers();
        List<SpiceUser> spiceUsers = new ArrayList<>();
        if (!allSpiceUsers.isEmpty()) {
            for (Map<String, Object> result : allSpiceUsers) {
                SpiceUser spiceUser = mapper.convertValue(result, SpiceUser.class);
                spiceUsers.add(spiceUser);
            }
        } else {
            Logger.logInfo("No data from spice user.");
        }
        return spiceUsers;
    }
}

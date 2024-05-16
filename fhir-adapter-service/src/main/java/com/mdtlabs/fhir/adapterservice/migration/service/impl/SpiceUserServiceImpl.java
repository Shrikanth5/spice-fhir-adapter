package com.mdtlabs.fhir.adapterservice.migration.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /**
     * Instantiates a new Spice user service.
     *
     * @param userMigrationRepository the user migration repository
     */
    public SpiceUserServiceImpl(SpiceUserMigrationRepository userMigrationRepository) {
        this.userMigrationRepository = userMigrationRepository;
    }

    @Override
    public List<SpiceUser> getAllSpiceUsers() {
        List<Map<String, Object>> allSpiceUsers = userMigrationRepository.findAllUsers();
        List<SpiceUser> spiceUsers = new ArrayList<>();
        if (!allSpiceUsers.isEmpty()) {
            for (Map<String, Object> result : allSpiceUsers) {
                SpiceUser spiceUser = convertUser(result);
                spiceUsers.add(spiceUser);
            }
        } else {
            Logger.logInfo("No data from spice user.");
        }
        return spiceUsers;
    }

     /**
     * method to map the resultSet data to respective user.
     *
     * @param result resultSet of SpiceUser.
     * @return converted User.
     */
    private SpiceUser convertUser(Map<String, Object> result) {
        SpiceUser user = new SpiceUser();
        user.setActive(result.get("is_active") != null);
        user.setCountryCode(String.valueOf(result.get("country_code")));
        user.setFirstName((String) result.get("first_name"));
        user.setAddress((String) result.get("address"));
        user.setLastName((String) result.get("last_name"));
        user.setMiddleName((String) result.get("middle_name"));
        user.setId(Long.valueOf((Integer) result.get("id")));
        user.setGender((String) result.get("gender"));
        user.setUsername((String) result.get("username"));
        user.setPhoneNumber((String) result.get("phone_number"));
        return user;
    }
}

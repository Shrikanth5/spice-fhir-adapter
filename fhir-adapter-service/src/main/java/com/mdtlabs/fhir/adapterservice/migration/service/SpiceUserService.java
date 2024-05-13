package com.mdtlabs.fhir.adapterservice.migration.service;

import com.mdtlabs.fhir.adapterservice.migration.model.SpiceUser;

import java.sql.Timestamp;
import java.util.List;

/**
 * The interface Spice user service.
 * Author: Akash Gopinath
 * Created on: April 10, 2024
 */
public interface SpiceUserService {

    /**
     * Gets all spice users.
     *
     * @return the all spice users
     */
    List<SpiceUser> getAllSpiceUsers();
}

package com.mdtlabs.fhir.fhiruserservice.service;

import com.mdtlabs.fhir.commonservice.common.model.entity.Role;

/**
 * RoleService interface defines methods for managing roles.
 * Author: Akash Gopinath
 * Created on: February 12, 2024
 */
public interface RoleService {

    /**
     * Retrieves a role by its unique identifier.
     *
     * @param roleId The identifier of the role to retrieve.
     * @return The Role object associated with the given roleId, or null if not found.
     */
    Role getRoleById(Long roleId);

}

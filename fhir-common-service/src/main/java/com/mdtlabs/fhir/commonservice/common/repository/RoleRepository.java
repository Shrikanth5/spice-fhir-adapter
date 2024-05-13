package com.mdtlabs.fhir.commonservice.common.repository;

import com.mdtlabs.fhir.commonservice.common.constants.FieldConstants;
import com.mdtlabs.fhir.commonservice.common.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * The {@code RoleRepository} interface defines the repository for role entities.
 * It provides methods for interacting with role data in the database.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, PagingAndSortingRepository<Role, Long> {

    String GET_ALL_ROLES = "select role from Role as role where role.isActive = :status";

    /**
     * <p>
     * Retrieves a list of roles from the database, filtered by their active status.
     * </p>
     *
     * @param status The status used to filter the list of roles that have
     *               the specified active status.
     * @return {@link List<Role>} The list of roles that are either active or
     * inactive is retrieved and returned from the database.
     */
    @Query(value = GET_ALL_ROLES)
    List<Role> getAllRoles(@Param(FieldConstants.STATUS) boolean status);
}
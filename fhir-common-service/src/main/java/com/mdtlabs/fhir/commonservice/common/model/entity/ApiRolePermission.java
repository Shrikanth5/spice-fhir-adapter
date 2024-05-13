package com.mdtlabs.fhir.commonservice.common.model.entity;

import com.mdtlabs.fhir.commonservice.common.constants.FieldConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * <p>
 * ApiRolePermission class representing a table called "ApiRolePermission" with columns for ID, method, API, and roles.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
@Entity
@Table(name = FieldConstants.TABLE_API_ROLE_PERMISSION)
public class ApiRolePermission {

    @Id
    @Column(name = FieldConstants.ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = FieldConstants.METHOD)
    private String method;

    @Column(name = FieldConstants.API)
    private String api;

    @Column(name = FieldConstants.ROLES)
    private String roles;
}

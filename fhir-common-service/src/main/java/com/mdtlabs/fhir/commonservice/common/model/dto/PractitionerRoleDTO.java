package com.mdtlabs.fhir.commonservice.common.model.dto;

import jakarta.persistence.Transient;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * This class is an entity class for practitioner role table.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
public class PractitionerRoleDTO {
    private long fhirRoleId;

    private long id;

    private String name;

    private String level;
    @Transient
    private String authority;

    private Long createdBy;

    private Long updatedBy;

    private Date createdAt;

    private Date updatedAt;

    private boolean isActive;

    private boolean isDeleted;

}
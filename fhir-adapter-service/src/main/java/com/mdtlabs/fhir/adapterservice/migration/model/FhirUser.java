package com.mdtlabs.fhir.adapterservice.migration.model;

import lombok.Data;

import java.sql.Date;

/**
 * The type Fhir user.
 * Author: Akash Gopinath
 * Created on: April 10, 2024
 */
@Data
public class FhirUser {
    private Long id;
    private Long spiceUserId;
    private Long fhirPractitionerId;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isActive;
    private Boolean isDeleted;
    private Long createdBy;
    private Long updatedBy;
}

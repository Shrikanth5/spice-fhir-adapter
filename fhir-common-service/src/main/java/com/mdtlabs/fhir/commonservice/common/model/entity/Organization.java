package com.mdtlabs.fhir.commonservice.common.model.entity;

import com.mdtlabs.fhir.commonservice.common.constants.FieldConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The {@code Organization} class represents an entity that stores information about organizations,
 * including their form data ID, form name, name, and parent organization ID.
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = FieldConstants.ORGANIZATION)
public class Organization extends BaseEntity {


    @Column(name = FieldConstants.FORM_DATA_ID)
    private Long formDataId;


    @Column(name = FieldConstants.FORM_NAME)
    private String formName;


    @Column(name = FieldConstants.NAME)
    private String name;


    @Column(name = FieldConstants.PARENT_ORGANIZATION_ID)
    private Long parentOrganizationId;
}
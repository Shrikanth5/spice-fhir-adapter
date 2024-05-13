package com.mdtlabs.fhir.commonservice.common.model.entity;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.constants.FieldConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

/**
 * The {@code Audit} class represents an entity for auditing purposes. It extends
 * {@link BaseEntity} and includes fields to record audit-related information such as
 * the entity name, action performed, entity ID, column name, old value, and new value.
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Entity
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = FieldConstants.AUDIT)
public class Audit {

    @Id
    @Column(name = FieldConstants.ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = FieldConstants.ENTITY)
    private String entity;

    @Column(name = FieldConstants.ACTION)
    private String action;

    @Column(name = FieldConstants.ENTITY_ID)
    private Long entityId;

    @Column(name = FieldConstants.COLUMN_NAME)
    private String columnName;

    @Column(name = FieldConstants.OLD_VALUE)
    private String oldValue;

    @Column(name = FieldConstants.NEW_VALUE)
    private String newValue;

    @Column(name = FieldConstants.CREATED_BY)
    private Long createdBy;

    @Column(name = FieldConstants.UPDATED_BY)
    private Long updatedBy;

    @Column(name = FieldConstants.CREATED_AT, columnDefinition = Constants.TIMESTAMP, nullable = false, updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = FieldConstants.UPDATED_AT, columnDefinition = Constants.TIMESTAMP)
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}

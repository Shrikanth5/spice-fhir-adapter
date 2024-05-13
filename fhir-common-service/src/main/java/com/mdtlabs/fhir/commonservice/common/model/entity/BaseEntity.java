package com.mdtlabs.fhir.commonservice.common.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.constants.FieldConstants;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * The {@code BaseEntity} class represents an entity that stores information about id,active,,
 * including their createdBy and updatedBy.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
@MappedSuperclass
@SuperBuilder(toBuilder = true)
public class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 4174505913611242103L;
    @Column(name = FieldConstants.CREATED_BY, updatable = false, nullable = false)
    private final Long createdBy = getUserValue();
    @Column(name = FieldConstants.UPDATED_BY)
    private final Long updatedBy = getUserValue();
    @Id
    @Column(name = FieldConstants.ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = FieldConstants.CREATED_AT, columnDefinition = Constants.TIMESTAMP, nullable = false, updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = FieldConstants.UPDATED_AT, columnDefinition = Constants.TIMESTAMP)
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = FieldConstants.IS_ACTIVE)
    private boolean isActive;
    @Column(name = FieldConstants.IS_DELETED)
    private boolean isDeleted;

    public BaseEntity() {

    }

    public BaseEntity(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public long     getUserValue() {
        return Constants.LONG_ONE;
    }

    public boolean isActive() {
        return Constants.TRUE;
    }

    public boolean isDeleted() {
        return Constants.FALSE;
    }

}

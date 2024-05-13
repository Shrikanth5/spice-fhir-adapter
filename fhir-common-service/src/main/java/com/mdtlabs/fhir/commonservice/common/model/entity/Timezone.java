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
 * <p>
 * The {@code Timezone} class represents an entity that stores information about timezones,
 * including their description, abbreviation, and offset.
 * </p>
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
@Table(name = FieldConstants.TIMEZONE)
public class Timezone extends BaseEntity {

    @Column(name = FieldConstants.DESCRIPTION)
    private String description;

    @Column(name = FieldConstants.ABBREVIATION)
    private String abbreviation;

    @Column(name = FieldConstants.OFFSET)
    private String offset;
}

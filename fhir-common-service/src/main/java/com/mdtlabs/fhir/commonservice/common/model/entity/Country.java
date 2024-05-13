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
 * The {@code Country} class represents an entity that stores information about countries,
 * including their name, code, and abbreviation.
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
@Table(name = FieldConstants.COUNTRY)
public class Country extends BaseEntity {

    @Column(name = FieldConstants.NAME)
    private String name;


    @Column(name = FieldConstants.CODE)
    private Long code;

    @Column(name = FieldConstants.ABBREVIATION)
    private String abbreviation;
}

package com.mdtlabs.fhir.commonservice.common.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import com.mdtlabs.fhir.commonservice.common.constants.FieldConstants;
import com.mdtlabs.fhir.commonservice.common.constants.TableConstants;

@Data
@Entity
@Table(name = TableConstants.TABLE_USER_SPICE_FHIR_MAPPING)
public class UserSpiceFhirMapping extends BaseEntity {

    @Column(name = FieldConstants.SPICE_USER_ID)
    private Long spiceUserId;

    @Column(name = FieldConstants.FHIR_PRACTITIONER_ID)
    private Long fhirPractitionerId;

    public UserSpiceFhirMapping() {
    }

    public UserSpiceFhirMapping(Long spiceUserId, Long fhirPractitionerId, Boolean isActive, Boolean isDeleted) {
        this.spiceUserId = spiceUserId;
        this.fhirPractitionerId = fhirPractitionerId;
        this.setActive(isActive);
        this.setDeleted(isDeleted);
    }


}

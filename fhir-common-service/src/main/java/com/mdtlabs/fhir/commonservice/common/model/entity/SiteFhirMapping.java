package com.mdtlabs.fhir.commonservice.common.model.entity;

import com.mdtlabs.fhir.commonservice.common.constants.FieldConstants;
import com.mdtlabs.fhir.commonservice.common.constants.TableConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serial;

@Data
@Entity
@Table(name = TableConstants.TABLE_SITE_FHIR_MAPPING)
public class SiteFhirMapping extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = FieldConstants.SITE_SPICE_ID)
    private Long siteSpiceId;

    @Column(name = FieldConstants.FHIR_ORGANIZATION_ID)
    private Long fhirOrganizationId;


    public SiteFhirMapping() {
    }

    public SiteFhirMapping(Long siteSpiceId, Long fhirOrganizationId, Boolean isActive, Boolean isDeleted) {
        this.siteSpiceId = siteSpiceId;
        this.fhirOrganizationId = fhirOrganizationId;
        this.setDeleted(isDeleted);
        this.setActive(isActive);
    }
}

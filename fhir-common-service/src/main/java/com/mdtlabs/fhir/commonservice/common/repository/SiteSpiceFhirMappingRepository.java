package com.mdtlabs.fhir.commonservice.common.repository;

import com.mdtlabs.fhir.commonservice.common.constants.FhirConstants;
import com.mdtlabs.fhir.commonservice.common.model.entity.SiteFhirMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteSpiceFhirMappingRepository extends JpaRepository<SiteFhirMapping, Long> {

    String SELECT_ORGANIZATION_ID = "SELECT s.fhirOrganizationId FROM SiteFhirMapping s WHERE s.siteSpiceId = :siteSpiceId";

    @Query(value = SELECT_ORGANIZATION_ID)
    Long findFhirOrganizationIdBySiteSpiceId(@Param(FhirConstants.SITE_SPICE_ID) Long siteSpiceId);
}

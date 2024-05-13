package com.mdtlabs.fhir.adapterservice.migration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mdtlabs.fhir.commonservice.common.model.entity.SiteFhirMapping;

public interface FhirSiteMigrationRepository extends JpaRepository<SiteFhirMapping, Long> {

    @Query(value = "select siteSpiceId from SiteFhirMapping")
    List<Long> getAllSpiceSiteIds();
}

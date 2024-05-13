package com.mdtlabs.fhir.adapterservice.migration.model;

import lombok.Builder;
import lombok.Data;

/**
 * The type Site fhir mapping.
 * Author: Akash Gopinath
 * Created on: April 10, 2024
 */
@Data
@Builder
public class SiteFhirMapping {
    private Long siteId;
    private String organizationId;
}

package com.mdtlabs.fhir.commonservice.common.model.dto;

import lombok.Data;

/**
 * <p>
 * This DTO class handling the FhirSiteRequest details
 * </p>
 * Author: Akash Gopinath
 * Created on: April 08, 2024
 */
@Data
public class FhirOrganizationRequestDto {

    private String type;

    private Long id;

    private String name;

    private String addressType;

    private String addressUse;

    private String address1;

    private String address2;

    private String latitude;

    private String longitude;

    private String city;

    private String phoneNumber;

    private Float workingHours;

    private String postalCode;

    private String siteType;

    private Long countryId;

    private Long countyId;

    private Long subCountyId;

    private String mflCode;

    private boolean isActive;

    private String countryName;

    private String subCountyName;

}

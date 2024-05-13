package com.mdtlabs.fhir.adapterservice.organization.service;

import org.hl7.fhir.r4.model.Organization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.mdtlabs.fhir.adapterservice.fhirclient.FhirClient;
import com.mdtlabs.fhir.adapterservice.organization.service.impl.OrganizationServiceImpl;

import com.mdtlabs.fhir.commonservice.common.model.dto.FhirOrganizationRequestDto;
import com.mdtlabs.fhir.commonservice.common.model.entity.SiteFhirMapping;
import com.mdtlabs.fhir.commonservice.common.repository.SiteSpiceFhirMappingRepository;
import com.mdtlabs.fhir.commonservice.common.utils.TestDataProvider;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OrganizationServiceImplTest {

    @InjectMocks
    private OrganizationServiceImpl organizationService;

    @Mock
    private SiteSpiceFhirMappingRepository siteSpiceFhirMappingRepository;

    @Mock
    private FhirClient fhirClient;

    @Test
    void sendOrganizationsToFhirServerTest() {
        //given
        Organization organization = new Organization();
        String response = "1";
        //when
        when(fhirClient.sendOrganizationToFhirServer(organization)).thenReturn(response);
        //then
        String actualResponse = organizationService.sendOrganizationsToFhirServer(organization);
        Assertions.assertEquals(response, actualResponse);
    }

    @Test
    void createAndMapFhirOrganizationTest() {
        //given
        FhirOrganizationRequestDto requestDto = TestDataProvider.getFhirOrganizationRequestDTO();
        Organization organization = TestDataProvider.getFhirOrganization();
        SiteFhirMapping siteFhirMapping = new SiteFhirMapping(1l,
                1l, true, false);
        //when
        when(fhirClient.sendOrganizationToFhirServer(any(Organization.class))).thenReturn("1");
        when(siteSpiceFhirMappingRepository.save(siteFhirMapping)).thenReturn(siteFhirMapping);
        //then
        organizationService.createAndMapFhirOrganization(requestDto);
        verify(siteSpiceFhirMappingRepository, atLeastOnce()).save(any(SiteFhirMapping.class));
    }
}

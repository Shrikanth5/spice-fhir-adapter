package com.mdtlabs.fhir.adapterservice.practitioner.service.impl;

import ca.uhn.fhir.rest.client.exceptions.FhirClientConnectionException;
import com.mdtlabs.fhir.adapterservice.converter.PractitionerConverter;
import com.mdtlabs.fhir.adapterservice.fhirclient.FhirClient;
import com.mdtlabs.fhir.adapterservice.utils.TestUtility;
import com.mdtlabs.fhir.commonservice.common.exception.RequestTimeOutException;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirUserDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.PractitionerDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.PractitionerRoleDTO;
import com.mdtlabs.fhir.commonservice.common.model.entity.UserSpiceFhirMapping;
import com.mdtlabs.fhir.commonservice.common.repository.UserSpiceFhirMappingRepository;
import org.hl7.fhir.r4.model.Practitioner;
import org.hl7.fhir.r4.model.PractitionerRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {PractitionerServiceImpl.class})
@ExtendWith(SpringExtension.class)
class PractitionerServiceImplTest {

    @MockBean
    private FhirClient fhirClient;
    @MockBean
    private PractitionerConverter practitionerConverter;
    @Autowired
    private PractitionerServiceImpl practitionerServiceImpl;
    @MockBean
    private UserSpiceFhirMappingRepository userSpiceFhirMappingRepository;

    @Test
    void createAndMapFhirPractitionerFhirClientConnectionExceptionTest() {
        when(practitionerConverter.convertUserToFhirEntity(Mockito.any()))
                .thenThrow(new FhirClientConnectionException("An error occurred"));

        FhirUserDTO userDTO = TestUtility.createMockFhirUserRequestDto();

        assertThrows(FhirClientConnectionException.class,
                () -> practitionerServiceImpl.createAndMapFhirPractitioner(userDTO));

        verify(practitionerConverter).convertUserToFhirEntity(Mockito.any());
    }

    @Test
    void testCreateAndMapFhirPractitioner() throws FhirClientConnectionException {
        when(fhirClient.sendResourceToFHIR(Mockito.any())).thenReturn(new Practitioner());
        PractitionerDTO practitionerDTO = TestUtility.createMockPractitionerDTO();
        when(practitionerConverter.convertPractitionerToUserDto(Mockito.any())).thenReturn(practitionerDTO);
        when(practitionerConverter.convertUserToFhirEntity(Mockito.any())).thenReturn(new Practitioner());
        UserSpiceFhirMapping userSpiceFhirMapping = TestUtility.createMockUserSpiceFhirMapping();
        when(userSpiceFhirMappingRepository.save(Mockito.any())).thenReturn(userSpiceFhirMapping);
        FhirUserDTO userDTO = TestUtility.createMockFhirUserRequestDto();

        PractitionerDTO actualCreateAndMapFhirPractitionerResult = practitionerServiceImpl.createAndMapFhirPractitioner(userDTO);

        verify(practitionerConverter).convertPractitionerToUserDto(Mockito.any());
        verify(practitionerConverter).convertUserToFhirEntity(Mockito.any());
        verify(fhirClient).sendResourceToFHIR(Mockito.any());
        verify(userSpiceFhirMappingRepository).save(Mockito.any());
        assertSame(practitionerDTO, actualCreateAndMapFhirPractitionerResult);
    }

    @Test
    void testCreateAndMapFhirPractitionerRole() {
        when(practitionerConverter.convertUserRoleToFhirEntity(Mockito.any()))
                .thenThrow(new FhirClientConnectionException("An error occurred"));

        PractitionerRoleDTO roleDetails = TestUtility.createMockPractitionerRoleDTO();

        assertThrows(FhirClientConnectionException.class,
                () -> practitionerServiceImpl.createAndMapFhirPractitionerRole(roleDetails));

        verify(practitionerConverter).convertUserRoleToFhirEntity(Mockito.any());
    }

    @Test
    void testCreatePractitioner() throws FhirClientConnectionException {
        Practitioner practitioner = new Practitioner();
        when(fhirClient.sendResourceToFHIR(Mockito.any())).thenReturn(practitioner);

        Practitioner actualCreatePractitionerResult = practitionerServiceImpl.createPractitioner(new Practitioner());

        verify(fhirClient).sendResourceToFHIR(Mockito.any());
        assertSame(practitioner, actualCreatePractitionerResult);
    }

    @Test
    void testCreatePractitionerRole() throws FhirClientConnectionException {
        PractitionerRole practitionerRole = new PractitionerRole();
        when(fhirClient.sendResourceToFHIR(Mockito.any())).thenReturn(practitionerRole);

        PractitionerRole actualCreatePractitionerRoleResult = practitionerServiceImpl.createPractitionerRole(new PractitionerRole());

        verify(fhirClient).sendResourceToFHIR(Mockito.any());
        assertSame(practitionerRole, actualCreatePractitionerRoleResult);
    }

    @Test
    void testCreateAndMapFhirPractitionerConnectionException() {
        when(practitionerConverter.convertUserToFhirEntity(Mockito.any())).thenReturn(new Practitioner());
        when(fhirClient.sendResourceToFHIR(Mockito.any())).thenThrow(new FhirClientConnectionException("Connection timeout"));
        FhirUserDTO userDTO = TestUtility.createMockFhirUserRequestDto();

        assertThrows(RequestTimeOutException.class,
                () -> practitionerServiceImpl.createAndMapFhirPractitioner(userDTO));

        verify(practitionerConverter).convertUserToFhirEntity(Mockito.any());
        verify(fhirClient).sendResourceToFHIR(Mockito.any());
    }

    @Test
    void testCreateAndMapFhirPractitionerRoleConnectionException() {
        when(practitionerConverter.convertUserRoleToFhirEntity(Mockito.any())).thenReturn(new PractitionerRole());
        when(fhirClient.sendResourceToFHIR(Mockito.any())).thenThrow(new FhirClientConnectionException("Connection timeout"));
        PractitionerRoleDTO roleDetails = TestUtility.createMockPractitionerRoleDTO();

        assertThrows(RequestTimeOutException.class,
                () -> practitionerServiceImpl.createAndMapFhirPractitionerRole(roleDetails));

        verify(practitionerConverter).convertUserRoleToFhirEntity(Mockito.any());
        verify(fhirClient).sendResourceToFHIR(Mockito.any());
    }

}

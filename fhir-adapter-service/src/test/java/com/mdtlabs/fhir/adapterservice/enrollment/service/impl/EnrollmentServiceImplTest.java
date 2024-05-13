package com.mdtlabs.fhir.adapterservice.enrollment.service.impl;

import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.exceptions.FhirClientConnectionException;
import com.mdtlabs.fhir.adapterservice.converter.EnrollmentRequestConverter;
import com.mdtlabs.fhir.adapterservice.converter.EnrollmentResponseConverter;
import com.mdtlabs.fhir.adapterservice.converter.PatientConverter;
import com.mdtlabs.fhir.adapterservice.fhirclient.FhirClient;
import com.mdtlabs.fhir.adapterservice.util.UpdatePatientDetails;
import com.mdtlabs.fhir.adapterservice.utils.TestUtility;
import com.mdtlabs.fhir.commonservice.common.DataConflictException;
import com.mdtlabs.fhir.commonservice.common.constants.ErrorConstants;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotAcceptableException;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotFoundException;
import com.mdtlabs.fhir.commonservice.common.exception.FhirValidation;
import com.mdtlabs.fhir.commonservice.common.exception.RequestTimeOutException;
import com.mdtlabs.fhir.commonservice.common.exception.UnauthorizedException;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirEnrollmentRequestDto;
import com.mdtlabs.fhir.commonservice.common.model.dto.PatientDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.PatientUpdateRequestDTO;
import com.mdtlabs.fhir.commonservice.common.model.entity.SpiceFhirMapping;
import com.mdtlabs.fhir.commonservice.common.model.entity.UserSpiceFhirMapping;
import com.mdtlabs.fhir.commonservice.common.repository.SiteSpiceFhirMappingRepository;
import com.mdtlabs.fhir.commonservice.common.repository.SpiceFhirMappingRepository;
import com.mdtlabs.fhir.commonservice.common.repository.UserSpiceFhirMappingRepository;
import jakarta.validation.ValidationException;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {EnrollmentServiceImpl.class})
@ExtendWith(SpringExtension.class)
class EnrollmentServiceImplTest {
    @MockBean
    private EnrollmentRequestConverter enrollmentRequestConverter;

    @MockBean
    private EnrollmentResponseConverter enrollmentResponseConverter;

    @Autowired
    private EnrollmentServiceImpl enrollmentServiceImpl;

    @MockBean
    private FhirClient fhirClient;

    @MockBean
    private PatientConverter patientConverter;

    @MockBean
    private SiteSpiceFhirMappingRepository siteSpiceFhirMappingRepository;

    @MockBean
    private SpiceFhirMappingRepository spiceFhirMappingRepository;

    @MockBean
    private UpdatePatientDetails updatePatientDetails;

    @MockBean
    private UserSpiceFhirMappingRepository userSpiceFhirMappingRepository;

    /**
     * Method under test:
     * {@link EnrollmentServiceImpl#createFhirPatient(FhirEnrollmentRequestDto)}
     */
    @Test
    void testCreateFhirPatient() {
        SpiceFhirMapping spiceFhirMapping = TestUtility.getSpiceFhirMapping();
        when(spiceFhirMappingRepository.findBySpiceModuleIdAndSpiceModuleAndStatusAndFhirResourceType(Mockito.<Long>any(),
                Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(spiceFhirMapping);
        doNothing().when(updatePatientDetails).updateFailureStatus(Mockito.any(), Mockito.<Long>any());

        FhirEnrollmentRequestDto enrollmentRequestDTO = TestUtility.getFHIREnrollmentRequest();

        // Act and Assert
        assertThrows(DataConflictException.class, () -> enrollmentServiceImpl.createFhirPatient(enrollmentRequestDTO));
        verify(updatePatientDetails).updateFailureStatus(eq("Patient Not Enrolled in FHIR"), Mockito.<Long>any());
        verify(spiceFhirMappingRepository).findBySpiceModuleIdAndSpiceModuleAndStatusAndFhirResourceType(
                Mockito.<Long>any(), eq("Patient"), eq("PROCESSED"), eq("Patient"));


        when(spiceFhirMappingRepository.findBySpiceModuleIdAndSpiceModuleAndStatusAndFhirResourceType(Mockito.<Long>any(),
                Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(null);
        when(userSpiceFhirMappingRepository.findAllBySpiceUserIdIn(anyList())).thenReturn(Collections
                .singletonList(new UserSpiceFhirMapping(1L, 2L, true, false)));
        assertThrows(DataNotFoundException.class, () -> enrollmentServiceImpl.createFhirPatient(enrollmentRequestDTO));

        enrollmentRequestDTO.getPatient().setSiteId(1L);
        when(spiceFhirMappingRepository.findBySpiceModuleIdAndSpiceModuleAndStatusAndFhirResourceType(Mockito.<Long>any(),
                Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(null);
        when(userSpiceFhirMappingRepository.findAllBySpiceUserIdIn(anyList())).thenReturn(Collections
                .singletonList(new UserSpiceFhirMapping(1L, 2L, true, false)));
        when(siteSpiceFhirMappingRepository.findFhirOrganizationIdBySiteSpiceId(anyLong())).thenReturn(1L);
        when(enrollmentRequestConverter.createFhirBundle(any(FhirEnrollmentRequestDto.class), any(),
                anyLong(), anyLong(),
                anyLong())).thenReturn(TestUtility.createEnrollmentFhirBundle());
        when(fhirClient.sendBundleToHapiServer(any(Bundle.class)))
                .thenReturn(TestUtility.getEnrollmentBundleString());
        when(fhirClient.getPatientAndObservationDetails(anyString())).thenReturn(TestUtility.createEnrollmentFhirBundle());
        when(enrollmentResponseConverter.convertObservationToEnrollmentResponseDTO(any(Observation.class), any()))
                .thenReturn(TestUtility.getEnrollmentResponseDTO());
        assertNotNull(enrollmentServiceImpl.createFhirPatient(enrollmentRequestDTO));


        enrollmentRequestDTO.getPatient().setSiteId(1L);
        when(spiceFhirMappingRepository.findBySpiceModuleIdAndSpiceModuleAndStatusAndFhirResourceType(Mockito.<Long>any(),
                Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(null);
        when(userSpiceFhirMappingRepository.findAllBySpiceUserIdIn(anyList())).thenReturn(Collections
                .singletonList(new UserSpiceFhirMapping(1L, 2L, true, false)));
        when(siteSpiceFhirMappingRepository.findFhirOrganizationIdBySiteSpiceId(anyLong())).thenReturn(1L);
        when(enrollmentRequestConverter.createFhirBundle(any(FhirEnrollmentRequestDto.class), any(),
                anyLong(), anyLong(),
                anyLong())).thenReturn(TestUtility.createEnrollmentFhirBundle());
        when(fhirClient.sendBundleToHapiServer(any(Bundle.class)))
                .thenReturn(TestUtility.getEnrollmentBundleString());
        when(fhirClient.getPatientAndObservationDetails(anyString())).thenReturn(TestUtility.createEnrollmentFhirBundle());
        when(enrollmentResponseConverter.convertObservationToEnrollmentResponseDTO(any(Observation.class), any()))
                .thenReturn(TestUtility.getEnrollmentResponseDTO());
        assertNotNull(enrollmentServiceImpl.createFhirPatient(enrollmentRequestDTO));


        Bundle enrollmentFhirBundle = TestUtility.createEnrollmentFhirBundle();
        enrollmentFhirBundle.getEntry().get(0).setResource(null);
        when(fhirClient.getPatientAndObservationDetails(anyString())).thenReturn(enrollmentFhirBundle);
        assertThrows(DataNotAcceptableException.class, () -> enrollmentServiceImpl.createFhirPatient(enrollmentRequestDTO));
        enrollmentRequestDTO.setVillage(null);
        assertThrows(DataNotAcceptableException.class, () -> enrollmentServiceImpl.createFhirPatient(enrollmentRequestDTO));

        enrollmentRequestDTO.setVillage("village");
        when(fhirClient.sendBundleToHapiServer(any(Bundle.class))).thenThrow(new NullPointerException("sample"));
        assertThrows(Exception.class, () -> enrollmentServiceImpl.createFhirPatient(enrollmentRequestDTO));

        when(fhirClient.sendBundleToHapiServer(any(Bundle.class)))
                .thenThrow(new FhirClientConnectionException("Sample"));
        assertThrows(RequestTimeOutException.class, () -> enrollmentServiceImpl.createFhirPatient(enrollmentRequestDTO));

        when(fhirClient.sendBundleToHapiServer(any(Bundle.class)))
                .thenThrow(new RestClientException(ErrorConstants.INVALID_API_KEY));
        assertThrows(UnauthorizedException.class, () -> enrollmentServiceImpl.createFhirPatient(enrollmentRequestDTO));

        when(fhirClient.sendBundleToHapiServer(any(Bundle.class)))
                .thenThrow(new RestClientException("Sample"));
        assertThrows(RestClientException.class, () -> enrollmentServiceImpl.createFhirPatient(enrollmentRequestDTO));

        enrollmentRequestDTO.getPatient().setSiteId(1L);
        when(spiceFhirMappingRepository.findBySpiceModuleIdAndSpiceModuleAndStatusAndFhirResourceType(Mockito.<Long>any(),
                Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(null);
        when(userSpiceFhirMappingRepository.findAllBySpiceUserIdIn(anyList())).thenReturn(Collections
                .singletonList(new UserSpiceFhirMapping(1L, 2L, true, false)));
        when(siteSpiceFhirMappingRepository.findFhirOrganizationIdBySiteSpiceId(anyLong())).thenReturn(1L);
        when(enrollmentRequestConverter.createFhirBundle(any(FhirEnrollmentRequestDto.class), any(),
                anyLong(), anyLong(),
                anyLong())).thenReturn(TestUtility.createEnrollmentFhirBundle());
        when(fhirClient.sendBundleToHapiServer(any(Bundle.class)))
                .thenReturn(TestUtility.getEnrollmentBundleString());
        when(fhirClient.getPatientAndObservationDetails(anyString())).thenReturn(TestUtility.createEnrollmentFhirBundle());
        when(enrollmentResponseConverter.convertObservationToEnrollmentResponseDTO(any(Observation.class), any()))
                .thenReturn(TestUtility.getEnrollmentResponseDTO());
        when(siteSpiceFhirMappingRepository.findFhirOrganizationIdBySiteSpiceId(anyLong()))
                .thenThrow(new EmptyResultDataAccessException(1));
        assertThrows(DataNotFoundException.class, () -> enrollmentServiceImpl.createFhirPatient(enrollmentRequestDTO));

    }

    /**
     * Method under test:
     * {@link EnrollmentServiceImpl#updatePatientDetails(PatientUpdateRequestDTO)}
     */
    @Test
    void testUpdatePatientDetails() {
        SpiceFhirMapping spiceFhirMapping = TestUtility.createTestSpiceFhirMapping();

        when(spiceFhirMappingRepository.findBySpiceModuleIdAndSpiceModuleAndStatusAndFhirResourceType(Mockito.<Long>any(),
                Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(spiceFhirMapping);

        PatientUpdateRequestDTO patientUpdateRequestOne = TestUtility.getPatientUpdateRequest();
        patientUpdateRequestOne.getPatientDTO().setLevelOfEducation(null);
        assertThrows(ValidationException.class, () -> enrollmentServiceImpl.updatePatientDetails(patientUpdateRequestOne));
        verify(spiceFhirMappingRepository).findBySpiceModuleIdAndSpiceModuleAndStatusAndFhirResourceType(
                Mockito.<Long>any(), eq("Patient"), eq("PROCESSED"), eq("Patient"));

        PatientUpdateRequestDTO patientUpdateRequest = TestUtility.getPatientUpdateRequest();
        when(userSpiceFhirMappingRepository.findAllBySpiceUserIdIn(anyList())).thenReturn(Collections
                .singletonList(new UserSpiceFhirMapping(1L, 2L, true, false)));
        when(patientConverter.convertPatientToFhirBundleEntity(Mockito.any(), Mockito.<Long>any(),
                Mockito.<Long>any())).thenReturn(new Patient());
        when(fhirClient.updateResource(Mockito.any())).thenReturn(new MethodOutcome());
        assertNull(enrollmentServiceImpl.updatePatientDetails(patientUpdateRequest));

        when(spiceFhirMappingRepository.findBySpiceModuleIdAndSpiceModuleAndStatusAndFhirResourceType(Mockito.<Long>any(),
                Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(null);
        assertThrows(ValidationException.class, () -> enrollmentServiceImpl.updatePatientDetails(patientUpdateRequestOne));
    }

    @Test
    public void testFhirValidationExceptionIsThrown() {
        // given
        FhirEnrollmentRequestDto enrollmentRequestDTO = new FhirEnrollmentRequestDto();
        enrollmentRequestDTO.setPatient(new PatientDTO());
        enrollmentRequestDTO.getPatient().setSiteId(1L);
        // then
        assertThrows(FhirValidation.class, () -> {
            enrollmentServiceImpl.createFhirPatient(enrollmentRequestDTO);
        });
    }
}


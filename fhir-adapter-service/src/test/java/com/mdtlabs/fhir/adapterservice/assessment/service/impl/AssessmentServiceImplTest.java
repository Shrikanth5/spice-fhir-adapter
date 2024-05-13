package com.mdtlabs.fhir.adapterservice.assessment.service.impl;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.client.exceptions.FhirClientConnectionException;
import com.mdtlabs.fhir.adapterservice.converter.AssessmentRequestConverter;
import com.mdtlabs.fhir.adapterservice.converter.AssessmentResponseConverter;
import com.mdtlabs.fhir.adapterservice.fhirclient.FhirClient;
import com.mdtlabs.fhir.adapterservice.util.UpdatePatientDetails;
import com.mdtlabs.fhir.adapterservice.utils.TestUtility;
import com.mdtlabs.fhir.commonservice.common.constants.ErrorConstants;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotAcceptableException;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotFoundException;
import com.mdtlabs.fhir.commonservice.common.exception.FhirValidation;
import com.mdtlabs.fhir.commonservice.common.exception.RequestTimeOutException;
import com.mdtlabs.fhir.commonservice.common.exception.UnauthorizedException;
import com.mdtlabs.fhir.commonservice.common.model.dto.AssessmentResponseDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.BpLogDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirAssessmentRequestDto;
import com.mdtlabs.fhir.commonservice.common.model.dto.GlucoseLogDTO;
import com.mdtlabs.fhir.commonservice.common.model.entity.UserSpiceFhirMapping;
import com.mdtlabs.fhir.commonservice.common.repository.SpiceFhirMappingRepository;
import com.mdtlabs.fhir.commonservice.common.repository.UserSpiceFhirMappingRepository;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Observation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {AssessmentServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AssessmentServiceImplTest {

    @Autowired
    private AssessmentServiceImpl assessmentService;
    @MockBean
    private FhirClient fhirClient;
    @MockBean
    private SpiceFhirMappingRepository spiceFhirMappingRepository;
    @MockBean
    private UserSpiceFhirMappingRepository userSpiceFhirMappingRepository;
    @MockBean
    private UpdatePatientDetails updatePatientDetails;
    @MockBean
    private AssessmentResponseConverter assessmentResponseConverter;
    @MockBean
    private AssessmentRequestConverter assessmentRequestConverter;


    @Test
    void createFhirObservationWithRestClientExceptionShouldThrowRequestTimeOutException() {
        FhirAssessmentRequestDto assessmentRequestDTO = new FhirAssessmentRequestDto();
        assessmentRequestDTO.setBpLog(new BpLogDTO());
        assessmentRequestDTO.setGlucoseLog(new GlucoseLogDTO());
        when(fhirClient.sendBundleToHapiServer(any())).thenThrow(RestClientException.class);
        assertThrows(DataNotFoundException.class, () -> assessmentService.createFhirObservation(assessmentRequestDTO, 123L));
    }

    @Test
    void createFhirObservationWithResourceValidationFailed() {
        FhirAssessmentRequestDto assessmentRequestDTO = new FhirAssessmentRequestDto();
        assessmentRequestDTO.setBpLog(null);
        assessmentRequestDTO.setGlucoseLog(null);
        assertThrows(FhirValidation.class, () -> assessmentService.createFhirObservation(assessmentRequestDTO, 123L));
    }

    @Test
    void createFhirObservationWithInvalidApiKeyShouldThrowUnauthorizedException() {
        FhirAssessmentRequestDto assessmentRequestDTO = new FhirAssessmentRequestDto();
        assessmentRequestDTO.setBpLog(new BpLogDTO());
        assessmentRequestDTO.setGlucoseLog(new GlucoseLogDTO());
        when(fhirClient.sendBundleToHapiServer(any())).thenThrow(new RestClientException("Invalid API key"));
        assertThrows(DataNotFoundException.class, () -> assessmentService.createFhirObservation(assessmentRequestDTO, 123L));
    }

    @Test
    void createFhirObservationWithUnknownExceptionShouldThrowException() {
        FhirAssessmentRequestDto assessmentRequestDTO = new FhirAssessmentRequestDto();
        assessmentRequestDTO.setBpLog(new BpLogDTO());
        assessmentRequestDTO.setGlucoseLog(new GlucoseLogDTO());
        when(fhirClient.sendBundleToHapiServer(any())).thenThrow(new RuntimeException("Unknown exception"));
        assertThrows(RuntimeException.class, () -> assessmentService.createFhirObservation(assessmentRequestDTO, 123L));
    }

    @Test
    void createFhirObservationWithValidDataShouldReturnAssessmentResponseDTO() {
        AssessmentResponseDTO expectedResponse = new AssessmentResponseDTO();

        when(fhirClient.sendBundleToHapiServer(any())).thenReturn("{}");
        when(assessmentResponseConverter.convertObservationToAssessmentResponseDTO(any(), any())).thenReturn(expectedResponse);
        when(userSpiceFhirMappingRepository.findAllBySpiceUserIdIn(anyList())).thenReturn(Collections
                .singletonList(new UserSpiceFhirMapping(1L, 2L, true, false)));
        when(spiceFhirMappingRepository.getFhirPatientId(anyLong(), anyString(), anyString(), anyString())).thenReturn(Optional.of(1L));

        FhirContext fhirContextMock = mock(FhirContext.class);
        Bundle bundleMock = mock(Bundle.class);
        IParser parserMock = mock(IParser.class);


        when(fhirContextMock.newJsonParser()).thenReturn(parserMock);
        when(spiceFhirMappingRepository.getFhirPatientId(anyLong(), anyString(), anyString(), anyString())).thenReturn(Optional.of(1L));

        when(fhirClient.sendBundleToHapiServer(any(Bundle.class)))
                .thenReturn(TestUtility.getAssessmentBundleString());

        when(fhirContextMock.newJsonParser().parseResource(any(), anyString()))
                .thenReturn(bundleMock);

        Bundle.BundleEntryComponent entryMock = mock(Bundle.BundleEntryComponent.class);
        when(bundleMock.getEntry()).thenReturn(Collections.singletonList(entryMock));
        FhirAssessmentRequestDto fhirAssessmentRequest = TestUtility.getFhirAssessmentRequest();

        when(assessmentRequestConverter.createFhirBundle(any(FhirAssessmentRequestDto.class), anyString(), anyLong())).thenReturn(TestUtility.getAssessmentBundle());
        when(fhirClient.getResourceDetails(anyString(), any(Observation.class))).thenReturn(TestUtility.getBPObservation());

        AssessmentResponseDTO actualResponse;
        actualResponse = assessmentService.createFhirObservation(fhirAssessmentRequest, fhirAssessmentRequest.getPatientTrackId());
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void createFhirObservationWithUnknownExceptionShouldThrowRequestTimeOutException() {
        AssessmentResponseDTO expectedResponse = new AssessmentResponseDTO();

        when(fhirClient.sendBundleToHapiServer(any())).thenThrow(new FhirClientConnectionException("FhirClientConnectionException"));
        when(assessmentResponseConverter.convertObservationToAssessmentResponseDTO(any(), any())).thenReturn(expectedResponse);
        when(userSpiceFhirMappingRepository.findAllBySpiceUserIdIn(anyList())).thenReturn(Collections
                .singletonList(new UserSpiceFhirMapping(1L, 2L, true, false)));

        FhirContext fhirContextMock = mock(FhirContext.class);
        Bundle bundleMock = mock(Bundle.class);
        IParser parserMock = mock(IParser.class);


        when(fhirContextMock.newJsonParser()).thenReturn(parserMock);
        when(spiceFhirMappingRepository.getFhirPatientId(anyLong(), anyString(), anyString(), anyString())).thenReturn(Optional.of(1L));


        when(fhirContextMock.newJsonParser().parseResource(any(), anyString()))
                .thenReturn(bundleMock);

        Bundle.BundleEntryComponent entryMock = mock(Bundle.BundleEntryComponent.class);
        when(bundleMock.getEntry()).thenReturn(Collections.singletonList(entryMock));
        FhirAssessmentRequestDto fhirAssessmentRequest = TestUtility.getFhirAssessmentRequest();

        when(assessmentRequestConverter.createFhirBundle(any(FhirAssessmentRequestDto.class), anyString(), anyLong())).thenReturn(TestUtility.getAssessmentBundle());
        when(fhirClient.getResourceDetails(anyString(), any(Observation.class))).thenReturn(TestUtility.getBPObservation());

        assertThrows(RequestTimeOutException.class, () -> assessmentService.createFhirObservation(fhirAssessmentRequest, fhirAssessmentRequest.getPatientTrackId()));
    }

    @Test
    void createFhirObservationWithUnknownExceptionShouldThrowRestClientException() {
        AssessmentResponseDTO expectedResponse = new AssessmentResponseDTO();

        when(fhirClient.sendBundleToHapiServer(any())).thenThrow(new RestClientException("RestClientException"));
        when(assessmentResponseConverter.convertObservationToAssessmentResponseDTO(any(), any())).thenReturn(expectedResponse);
        when(userSpiceFhirMappingRepository.findAllBySpiceUserIdIn(anyList())).thenReturn(Collections
                .singletonList(new UserSpiceFhirMapping(1L, 2L, true, false)));

        FhirContext fhirContextMock = mock(FhirContext.class);
        Bundle bundleMock = mock(Bundle.class);
        IParser parserMock = mock(IParser.class);


        when(fhirContextMock.newJsonParser()).thenReturn(parserMock);
        when(spiceFhirMappingRepository.getFhirPatientId(anyLong(), anyString(), anyString(), anyString())).thenReturn(Optional.of(1L));


        when(fhirContextMock.newJsonParser().parseResource(any(), anyString()))
                .thenReturn(bundleMock);

        Bundle.BundleEntryComponent entryMock = mock(Bundle.BundleEntryComponent.class);
        when(bundleMock.getEntry()).thenReturn(Collections.singletonList(entryMock));
        FhirAssessmentRequestDto fhirAssessmentRequest = TestUtility.getFhirAssessmentRequest();

        when(assessmentRequestConverter.createFhirBundle(any(FhirAssessmentRequestDto.class), anyString(), anyLong())).thenReturn(TestUtility.getAssessmentBundle());
        when(fhirClient.getResourceDetails(anyString(), any(Observation.class))).thenReturn(TestUtility.getBPObservation());

        assertThrows(RestClientException.class, () -> assessmentService.createFhirObservation(fhirAssessmentRequest, fhirAssessmentRequest.getPatientTrackId()));
    }

    @Test
    void createFhirObservationWithUnknownExceptionShouldThrowUnauthorizedException() {
        AssessmentResponseDTO expectedResponse = new AssessmentResponseDTO();

        when(fhirClient.sendBundleToHapiServer(any())).thenThrow(new RestClientException(ErrorConstants.INVALID_API_KEY));
        when(assessmentResponseConverter.convertObservationToAssessmentResponseDTO(any(), any())).thenReturn(expectedResponse);
        when(userSpiceFhirMappingRepository.findAllBySpiceUserIdIn(anyList())).thenReturn(Collections
                .singletonList(new UserSpiceFhirMapping(1L, 2L, true, false)));

        FhirContext fhirContextMock = mock(FhirContext.class);
        Bundle bundleMock = mock(Bundle.class);
        IParser parserMock = mock(IParser.class);


        when(fhirContextMock.newJsonParser()).thenReturn(parserMock);
        when(spiceFhirMappingRepository.getFhirPatientId(anyLong(), anyString(), anyString(), anyString())).thenReturn(Optional.of(1L));


        when(fhirContextMock.newJsonParser().parseResource(any(), anyString()))
                .thenReturn(bundleMock);

        Bundle.BundleEntryComponent entryMock = mock(Bundle.BundleEntryComponent.class);
        when(bundleMock.getEntry()).thenReturn(Collections.singletonList(entryMock));
        FhirAssessmentRequestDto fhirAssessmentRequest = TestUtility.getFhirAssessmentRequest();

        when(assessmentRequestConverter.createFhirBundle(any(FhirAssessmentRequestDto.class), anyString(), anyLong())).thenReturn(TestUtility.getAssessmentBundle());
        when(fhirClient.getResourceDetails(anyString(), any(Observation.class))).thenReturn(TestUtility.getBPObservation());


        assertThrows(UnauthorizedException.class, () -> assessmentService.createFhirObservation(fhirAssessmentRequest, fhirAssessmentRequest.getPatientTrackId()));
    }

    @Test
    void createFhirObservationWithUnknownExceptionShouldThrowDataNotAcceptableException() {
        AssessmentResponseDTO expectedResponse = new AssessmentResponseDTO();

        when(fhirClient.sendBundleToHapiServer(any())).thenReturn("{}");
        when(assessmentResponseConverter.convertObservationToAssessmentResponseDTO(any(), any())).thenReturn(expectedResponse);
        when(userSpiceFhirMappingRepository.findAllBySpiceUserIdIn(anyList())).thenReturn(Collections
                .singletonList(new UserSpiceFhirMapping(1L, 2L, true, false)));
        when(spiceFhirMappingRepository.getFhirPatientId(anyLong(), anyString(), anyString(), anyString())).thenReturn(Optional.of(1L));

        FhirContext fhirContextMock = mock(FhirContext.class);
        Bundle bundleMock = mock(Bundle.class);
        IParser parserMock = mock(IParser.class);


        when(fhirContextMock.newJsonParser()).thenReturn(parserMock);
        when(spiceFhirMappingRepository.getFhirPatientId(anyLong(), anyString(), anyString(), anyString())).thenReturn(Optional.of(1L));

        when(fhirClient.sendBundleToHapiServer(any(Bundle.class)))
                .thenReturn(TestUtility.getIncorrectBundleString());

        when(fhirContextMock.newJsonParser().parseResource(any(), anyString()))
                .thenReturn(bundleMock);

        Bundle.BundleEntryComponent entryMock = mock(Bundle.BundleEntryComponent.class);
        when(bundleMock.getEntry()).thenReturn(Collections.singletonList(entryMock));
        FhirAssessmentRequestDto fhirAssessmentRequest = TestUtility.getFhirAssessmentRequest();

        when(assessmentRequestConverter.createFhirBundle(any(FhirAssessmentRequestDto.class), anyString(), anyLong())).thenReturn(TestUtility.getAssessmentBundle());
        when(fhirClient.getResourceDetails(anyString(), any(Observation.class))).thenReturn(TestUtility.createSampleIncorrectObservation());

        assertThrows(DataNotAcceptableException.class, () -> assessmentService.createFhirObservation(fhirAssessmentRequest, fhirAssessmentRequest.getPatientTrackId()));
    }

    @Test
    void createFhirObservationWithUnknownExceptionShouldThrowRequestTimeOutExceptionAnother() {
        AssessmentResponseDTO expectedResponse = new AssessmentResponseDTO();

        when(fhirClient.sendBundleToHapiServer(any())).thenReturn("{}");
        when(assessmentResponseConverter.convertObservationToAssessmentResponseDTO(any(), any())).thenReturn(expectedResponse);
        when(userSpiceFhirMappingRepository.findAllBySpiceUserIdIn(anyList())).thenReturn(Collections
                .singletonList(new UserSpiceFhirMapping(1L, 2L, true, false)));
        when(spiceFhirMappingRepository.getFhirPatientId(anyLong(), anyString(), anyString(), anyString())).thenReturn(Optional.of(1L));

        FhirContext fhirContextMock = mock(FhirContext.class);
        Bundle bundleMock = mock(Bundle.class);
        IParser parserMock = mock(IParser.class);


        when(fhirContextMock.newJsonParser()).thenReturn(parserMock);
        when(spiceFhirMappingRepository.getFhirPatientId(anyLong(), anyString(), anyString(), anyString())).thenReturn(Optional.of(1L));

        when(fhirClient.sendBundleToHapiServer(any(Bundle.class)))
                .thenReturn(TestUtility.getAssessmentBundleString());

        when(fhirContextMock.newJsonParser().parseResource(any(), anyString()))
                .thenReturn(bundleMock);

        Bundle.BundleEntryComponent entryMock = mock(Bundle.BundleEntryComponent.class);
        when(bundleMock.getEntry()).thenReturn(Collections.singletonList(entryMock));
        FhirAssessmentRequestDto fhirAssessmentRequest = TestUtility.getFhirAssessmentRequest();

        when(assessmentRequestConverter.createFhirBundle(any(FhirAssessmentRequestDto.class), anyString(), anyLong())).thenReturn(TestUtility.getAssessmentBundle());
        when(fhirClient.getResourceDetails(anyString(), any(Observation.class))).thenReturn(TestUtility.getBGObservation());

        AssessmentResponseDTO actualResponse = assessmentService.createFhirObservation(fhirAssessmentRequest, fhirAssessmentRequest.getPatientTrackId());
        assertEquals(expectedResponse, actualResponse);

        when(fhirClient.getResourceDetails(anyString(), any(Observation.class))).thenReturn(TestUtility.createSampleIncorrectObservation());
        assertThrows(DataNotAcceptableException.class, () -> assessmentService.createFhirObservation(fhirAssessmentRequest, fhirAssessmentRequest.getPatientTrackId()));

        when(fhirClient.getResourceDetails(anyString(), any(Observation.class))).thenThrow(new FhirClientConnectionException("FhirClientConnectionException"));
        assertThrows(RequestTimeOutException.class, () -> assessmentService.createFhirObservation(fhirAssessmentRequest, fhirAssessmentRequest.getPatientTrackId()));
    }
}

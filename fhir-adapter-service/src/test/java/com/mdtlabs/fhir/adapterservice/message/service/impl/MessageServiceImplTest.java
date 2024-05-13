package com.mdtlabs.fhir.adapterservice.message.service.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdtlabs.fhir.adapterservice.assessment.service.impl.AssessmentServiceImpl;
import com.mdtlabs.fhir.adapterservice.enrollment.service.impl.EnrollmentServiceImpl;
import com.mdtlabs.fhir.adapterservice.organization.service.OrganizationService;
import com.mdtlabs.fhir.adapterservice.practitioner.service.impl.PractitionerServiceImpl;
import com.mdtlabs.fhir.adapterservice.utils.TestUtility;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.exception.BadRequestException;
import com.mdtlabs.fhir.commonservice.common.model.dto.*;
import com.mdtlabs.fhir.commonservice.common.model.entity.SpiceFhirMapping;
import com.mdtlabs.fhir.commonservice.common.repository.SpiceFhirMappingRepository;
import com.mdtlabs.fhir.commonservice.common.utils.TestDataProvider;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {MessageServiceImpl.class})
@ExtendWith(SpringExtension.class)
class MessageServiceImplTest {
    @MockBean
    private AssessmentServiceImpl assessmentServiceImpl;

    @MockBean
    private EnrollmentServiceImpl enrollmentServiceImpl;

    @Autowired
    private MessageServiceImpl messageServiceImpl;

    @MockBean
    private ObjectMapper objectMapper;

    @MockBean
    private PractitionerServiceImpl practitionerServiceImpl;

    @MockBean
    private SpiceFhirMappingRepository spiceFhirMappingRepository;

    @MockBean
    private OrganizationService organizationService;

    /**
     * Method under test: {@link MessageServiceImpl#createMappingData(String)}
     */
    @Test
    void testCreateMappingDataEnrollment() throws JsonProcessingException {
        SpiceFhirMapping spiceFhirMapping = TestUtility.getFhirMapping();

        when(spiceFhirMappingRepository.save(any())).thenReturn(spiceFhirMapping);

        FhirEnrollmentRequestDto fhirEnrollmentRequestDto = TestUtility.getFHIREnrollmentRequest();
        GlucoseLogDTO glucoseLog = TestUtility.getFhirAssessmentRequest().getGlucoseLog();
        glucoseLog.setId(1L);
        fhirEnrollmentRequestDto.setGlucoseLog(glucoseLog);
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<FhirEnrollmentRequestDto>>any()))
                .thenReturn(fhirEnrollmentRequestDto);

        assertEquals(Constants.MESSAGE_SENT_TO_HAPI_SERVER_SUCCESSFULLY, messageServiceImpl.convertMessageToObject(TestUtility.getSpiceEnrollmentJsonMessageString()));

    }

    /**
     * Method under test: {@link MessageServiceImpl#createMappingData(String)}
     */
    @Test
    void testCreateMappingDataAssessment() throws JsonProcessingException {
        SpiceFhirMapping spiceFhirMapping = TestUtility.getFhirMapping();

        when(spiceFhirMappingRepository.save(any())).thenReturn(spiceFhirMapping);


        FhirAssessmentRequestDto fhirAssessmentRequestDto = TestUtility.getFhirAssessmentRequest();
        GlucoseLogDTO glucoseLog = TestUtility.getFhirAssessmentRequest().getGlucoseLog();
        glucoseLog.setId(1L);
        fhirAssessmentRequestDto.setGlucoseLog(glucoseLog);
        when(objectMapper.readValue(anyString(), Mockito.<Class<FhirAssessmentRequestDto>>any()))
                .thenReturn(fhirAssessmentRequestDto);
        assertEquals(Constants.MESSAGE_SENT_TO_HAPI_SERVER_SUCCESSFULLY, messageServiceImpl.convertMessageToObject(TestUtility.getSpiceAssessmentJsonMessageString()));

    }

    /**
     * Method under test: {@link MessageServiceImpl#convertMessageToObject(String)}
     */
    @Test
    void testConvertMessageToObjectEnrollment() throws JsonProcessingException {

        FhirEnrollmentRequestDto fhirEnrollmentRequestDto = TestUtility.getFHIREnrollmentRequest();

        GlucoseLogDTO glucoseLog = TestUtility.getFhirAssessmentRequest().getGlucoseLog();
        glucoseLog.setId(1L);

        fhirEnrollmentRequestDto.setGlucoseLog(glucoseLog);

        BpLogDTO bpLog = TestUtility.getFhirAssessmentRequest().getBpLog();
        bpLog.setId(1L);

        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<FhirEnrollmentRequestDto>>any()))
                .thenReturn(fhirEnrollmentRequestDto);

        assertEquals(Constants.MESSAGE_SENT_TO_HAPI_SERVER_SUCCESSFULLY, messageServiceImpl.convertMessageToObject(TestUtility.getSpiceEnrollmentJsonMessageString()));

    }

    /**
     * Method under test: {@link MessageServiceImpl#convertMessageToObject(String)}
     */
    @Test
    void testConvertMessageToObjectAssessment() throws JsonProcessingException {

        FhirAssessmentRequestDto fhirAssessmentRequestDto = TestUtility.getFhirAssessmentRequest();

        GlucoseLogDTO glucoseLog = TestUtility.getFhirAssessmentRequest().getGlucoseLog();
        glucoseLog.setId(1L);

        fhirAssessmentRequestDto.setGlucoseLog(glucoseLog);

        BpLogDTO bpLog = TestUtility.getFhirAssessmentRequest().getBpLog();
        bpLog.setId(1L);
        fhirAssessmentRequestDto.setBpLog(bpLog);


        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<FhirAssessmentRequestDto>>any()))
                .thenReturn(fhirAssessmentRequestDto);

        assertEquals(Constants.MESSAGE_SENT_TO_HAPI_SERVER_SUCCESSFULLY, messageServiceImpl.convertMessageToObject(TestUtility.getSpiceAssessmentJsonMessageString()));


    }

    /**
     * Method under test: {@link MessageServiceImpl#convertMessageToObject(String)}
     */
    @Test
    void testConvertMessageToObjectUser() throws JsonProcessingException {

        PractitionerDTO practitionerDTO = TestUtility.createMockPractitionerDTO();
        FhirUserRequestDTO fhirUserRequestDto = new FhirUserRequestDTO();
        List<FhirUserDTO> fhirUserDTOS = new ArrayList<>();
        fhirUserDTOS.add(TestDataProvider.getFhirUserDTO());
        fhirUserRequestDto.setUsers(fhirUserDTOS);
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<FhirUserRequestDTO>>any())).thenReturn(fhirUserRequestDto);

        when(practitionerServiceImpl.createAndMapFhirPractitioner(any(FhirUserDTO.class)))
                .thenReturn(practitionerDTO);

        assertEquals(Constants.MESSAGE_SENT_TO_HAPI_SERVER_SUCCESSFULLY, messageServiceImpl.convertMessageToObject(TestUtility.getSpiceUserJsonMessageString()));
    }


    /**
     * Method under test: {@link MessageServiceImpl#convertMessageToObject(String)}
     */
    @Test
    void testConvertMessageToObjectException() {
        assertThrows(BadRequestException.class, () -> messageServiceImpl.convertMessageToObject(TestUtility.getSpiceInvalidJsonMessageString()));
    }

    @Test
    void createMappingDataProcessingEnrollmentRequestDataTest() throws JsonProcessingException {
        // given
        FhirEnrollmentRequestDto fhirEnrollmentRequestDto = TestDataProvider.getFhirEnrollmentRequestDTO();
        SpiceFhirMapping spiceFhirMapping = TestDataProvider.getSpiceFhirMapping();
        // when
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<FhirEnrollmentRequestDto>>any())).thenReturn(fhirEnrollmentRequestDto);
        messageServiceImpl.createMappingData(Constants.ENROLLMENT_DATA);
        // then
        verify(spiceFhirMappingRepository, atLeast(1)).save(spiceFhirMapping);
    }

    @Test
    void createMappingDataProcessingAssessmentRequestDataTest() throws JsonProcessingException {
        // given
        FhirAssessmentRequestDto fhirAssessmentRequestDto = TestDataProvider.getFhirAssessmentRequestDto();
        SpiceFhirMapping spiceFhirMapping = TestDataProvider.getSpiceFhirMapping();
        // when
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<FhirAssessmentRequestDto>>any())).thenReturn(fhirAssessmentRequestDto);
        // then
        messageServiceImpl.createMappingData(Constants.ASSESSMENT_DATA);
        verify(spiceFhirMappingRepository, atLeast(1)).save(spiceFhirMapping);
    }

    @Test
    void testProcessingSiteRequestData() throws JsonProcessingException {
        // given
        FhirOrganizationRequestDto fhirOrganizationRequestDto = new FhirOrganizationRequestDto();
        // when
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<FhirOrganizationRequestDto>>any())).thenReturn(fhirOrganizationRequestDto);
        doNothing().when(organizationService).createAndMapFhirOrganization(fhirOrganizationRequestDto);
        // then
        assertEquals(Constants.MESSAGE_SENT_TO_HAPI_SERVER_SUCCESSFULLY, messageServiceImpl.convertMessageToObject(Constants.SITE_DATA));

    }

    @Test
    void testProcessingUserRequestDataPatientEdit() throws JsonProcessingException {
        // given
        PatientUpdateRequestDTO updateRequestDTO = new PatientUpdateRequestDTO();
        // when
        when(objectMapper.readValue(anyString(), eq(PatientUpdateRequestDTO.class))).thenReturn(updateRequestDTO);
        IBaseResource mockedResource = mock(IBaseResource.class);
        when(enrollmentServiceImpl.updatePatientDetails(updateRequestDTO)).thenReturn(mockedResource);
        String result = messageServiceImpl.convertMessageToObject(Constants.PATIENT_EDIT);
        // then
        assertEquals(Constants.MESSAGE_UPDATED_SUCCESSFULLY + mockedResource.getIdElement(), result);
    }
}

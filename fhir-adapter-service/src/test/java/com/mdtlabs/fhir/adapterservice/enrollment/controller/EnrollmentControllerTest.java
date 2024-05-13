package com.mdtlabs.fhir.adapterservice.enrollment.controller;

import ca.uhn.fhir.context.FhirVersionEnum;
import com.mdtlabs.fhir.adapterservice.enrollment.service.EnrollmentService;
import com.mdtlabs.fhir.adapterservice.util.SuccessResponse;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.model.dto.EnrollmentResponseDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirEnrollmentRequestDto;
import com.mdtlabs.fhir.commonservice.common.model.dto.PatientUpdateRequestDTO;
import org.hl7.fhir.instance.model.api.IBaseMetaType;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnrollmentControllerTest {

    @InjectMocks
    EnrollmentController enrollmentController;

    @Mock
    EnrollmentService enrollmentService;

    @Test
    void testCreateFHIRPatient() {
        //given
        FhirEnrollmentRequestDto enrollmentDetails = new FhirEnrollmentRequestDto();
        EnrollmentResponseDTO enrollmentResponseDTO = new EnrollmentResponseDTO();

        //when
        when(enrollmentService.createFhirPatient(enrollmentDetails))
                .thenReturn(enrollmentResponseDTO);
        SuccessResponse<EnrollmentResponseDTO> response = enrollmentController.createFHIRPatient(Constants.ACCESS_KEY_ID_PARAM,
                Constants.SECRET_ACCESS_KEY_PARAM, enrollmentDetails);

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdatePatientDetails() throws Exception {
        //given
        PatientUpdateRequestDTO requestDTO = new PatientUpdateRequestDTO();
        IBaseResource responseResource = new IBaseResource() {
            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean hasFormatComment() {
                return false;
            }

            @Override
            public List<String> getFormatCommentsPre() {
                return null;
            }

            @Override
            public List<String> getFormatCommentsPost() {
                return null;
            }

            @Override
            public Object getUserData(String s) {
                return null;
            }

            @Override
            public void setUserData(String s, Object o) {
            }

            @Override
            public IBaseMetaType getMeta() {
                return null;
            }

            @Override
            public IIdType getIdElement() {
                return null;
            }

            @Override
            public IBaseResource setId(String s) {
                return null;
            }

            @Override
            public IBaseResource setId(IIdType iIdType) {
                return null;
            }

            @Override
            public FhirVersionEnum getStructureFhirVersionEnum() {
                return null;
            }
        };

        //when
        when(enrollmentService.updatePatientDetails(requestDTO))
                .thenReturn(responseResource);
        SuccessResponse<String> response = enrollmentController.updatePatientDetails("accessKey", "secretKey", requestDTO);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}

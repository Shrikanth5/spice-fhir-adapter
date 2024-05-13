package com.mdtlabs.fhir.adapterservice.assessment.controller;

import com.mdtlabs.fhir.adapterservice.assessment.service.AssessmentService;
import com.mdtlabs.fhir.adapterservice.util.SuccessResponse;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.model.dto.AssessmentResponseDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirAssessmentRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssessmentControllerTest {

    @InjectMocks
    AssessmentController assessmentController;

    @Mock
    AssessmentService assessmentService;

    @Test
    void testCreateObservation() {
        //given
        FhirAssessmentRequestDto assessmentRequest = new FhirAssessmentRequestDto();
        AssessmentResponseDTO assessmentResponseDTO = new AssessmentResponseDTO();

        //when
        when(assessmentService.createFhirObservation(assessmentRequest, assessmentRequest.getPatientTrackId()))
                .thenReturn(assessmentResponseDTO);
        SuccessResponse<AssessmentResponseDTO> response = assessmentController.createObservation(Constants.ACCESS_KEY_ID_PARAM,
                Constants.SECRET_ACCESS_KEY_PARAM, assessmentRequest);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}

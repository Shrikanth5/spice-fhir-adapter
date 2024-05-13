package com.mdtlabs.fhir.adapterservice.assessment.controller;

import com.mdtlabs.fhir.adapterservice.assessment.service.AssessmentService;
import com.mdtlabs.fhir.adapterservice.util.SuccessCode;
import com.mdtlabs.fhir.adapterservice.util.SuccessResponse;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.model.dto.AssessmentResponseDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirAssessmentRequestDto;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Controller class handling assessment-related endpoints.
 * </p>
 * <p>
 * Author: Shrikanth
 * <p>
 * Created on: February 27, 2024
 */
@RestController
@RequestMapping(Constants.ASSESSMENT_URI)
public class AssessmentController {
    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    /**
     * <p>
     * Endpoint for creating a FHIR Observation based on the provided assessment request.
     * </p>
     *
     * @param accessKey         The access key ID for authentication.
     * @param secretKey         The secret access key for authentication.
     * @param assessmentRequest The FHIR assessment request DTO received in the request body.
     * @return A SuccessResponse containing the created AssessmentResponseDTO.
     */
    @PostMapping(Constants.CREATE_OBSERVATION_URI)
    public SuccessResponse<AssessmentResponseDTO> createObservation(
            @RequestHeader(Constants.ACCESS_KEY_ID_PARAM) String accessKey,
            @RequestHeader(Constants.SECRET_ACCESS_KEY_PARAM) String secretKey,
            @RequestBody FhirAssessmentRequestDto assessmentRequest) {
        return new SuccessResponse<>(SuccessCode.ASSESSMENT_SAVE,
                new ModelMapper().map(assessmentService.createFhirObservation(assessmentRequest,
                        assessmentRequest.getPatientTrackId()), AssessmentResponseDTO.class), HttpStatus.OK);
    }
}

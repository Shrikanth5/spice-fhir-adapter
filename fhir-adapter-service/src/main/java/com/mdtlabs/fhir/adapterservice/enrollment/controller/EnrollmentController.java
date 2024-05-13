package com.mdtlabs.fhir.adapterservice.enrollment.controller;

import com.mdtlabs.fhir.adapterservice.enrollment.service.EnrollmentService;
import com.mdtlabs.fhir.adapterservice.util.SuccessCode;
import com.mdtlabs.fhir.adapterservice.util.SuccessResponse;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.model.dto.EnrollmentResponseDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirEnrollmentRequestDto;
import com.mdtlabs.fhir.commonservice.common.model.dto.PatientUpdateRequestDTO;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class responsible for handling HTTP requests related to patient enrollment.
 * <p>
 * This class contains endpoints for creating enrollment requests for new patients.
 * </p>
 * <p>
 * Author: Charan
 * <p>
 * Created on: February 27, 2024
 */
@RestController
@RequestMapping(Constants.ENROLLMENT_URI)
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    /**
     * Handles the creation of an enrollment request data for a new patient.
     *
     * @param accessKey         The access key ID for authentication.
     * @param secretKey         The secret access key for authentication.
     * @param enrollmentDetails The enrollment request DTO containing patient information.
     * @return A SuccessResponse containing the enrollment response DTO and HTTP status.
     */
    @PostMapping(Constants.CREATE_URI)
    public SuccessResponse<EnrollmentResponseDTO> createFHIRPatient(
            @RequestHeader(Constants.ACCESS_KEY_ID_PARAM) String accessKey,
            @RequestHeader(Constants.SECRET_ACCESS_KEY_PARAM) String secretKey,
            @RequestBody FhirEnrollmentRequestDto enrollmentDetails) {

        return new SuccessResponse<>(SuccessCode.ENROLLMENT_SAVE, new ModelMapper().map(enrollmentService
                .createFhirPatient(enrollmentDetails), EnrollmentResponseDTO.class), HttpStatus.OK);
    }

    @PutMapping(Constants.UPDATE_USER_URI)
    public SuccessResponse<String> updatePatientDetails(
            @RequestHeader(Constants.ACCESS_KEY_ID_PARAM) String accessKey,
            @RequestHeader(Constants.SECRET_ACCESS_KEY_PARAM) String secretKey,
            @RequestBody PatientUpdateRequestDTO requestDTO) throws Exception {
        IBaseResource response = enrollmentService.updatePatientDetails(requestDTO);
        return new SuccessResponse<>(SuccessCode.PATIENT_UPDATE,
                response.getIdElement(), HttpStatus.OK);
    }

}

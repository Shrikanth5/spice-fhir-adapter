package com.mdtlabs.fhir.adapterservice.message.service.impl;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdtlabs.fhir.adapterservice.assessment.service.impl.AssessmentServiceImpl;
import com.mdtlabs.fhir.adapterservice.enrollment.service.impl.EnrollmentServiceImpl;
import com.mdtlabs.fhir.adapterservice.message.service.MessageService;
import com.mdtlabs.fhir.adapterservice.organization.service.OrganizationService;
import com.mdtlabs.fhir.adapterservice.practitioner.service.impl.PractitionerServiceImpl;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.constants.ErrorConstants;
import com.mdtlabs.fhir.commonservice.common.exception.BadRequestException;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirAssessmentRequestDto;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirEnrollmentRequestDto;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirOrganizationRequestDto;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirUserDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirUserRequestDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.PatientUpdateRequestDTO;
import com.mdtlabs.fhir.commonservice.common.model.entity.SpiceFhirMapping;
import com.mdtlabs.fhir.commonservice.common.repository.SpiceFhirMappingRepository;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.springframework.stereotype.Service;

/**
 * Service implementation for processing messages and managing FHIR mappings.
 * <p>
 * Author: Shrikanth
 * <p>
 * Created on: February 27, 2024
 */
@Service
public class MessageServiceImpl implements MessageService {

    private final SpiceFhirMappingRepository spiceFhirMappingRepository;

    private final ObjectMapper mapper;

    private final EnrollmentServiceImpl enrollmentService;

    private final AssessmentServiceImpl assessmentService;

    private final PractitionerServiceImpl userService;

    private final OrganizationService organizationService;

    public MessageServiceImpl(SpiceFhirMappingRepository spiceFhirMappingRepository,
                              ObjectMapper mapper, EnrollmentServiceImpl enrollmentService,
                              AssessmentServiceImpl assessmentService, PractitionerServiceImpl userService,
                              OrganizationService organizationService) {
        this.spiceFhirMappingRepository = spiceFhirMappingRepository;
        this.mapper = mapper;
        this.enrollmentService = enrollmentService;
        this.assessmentService = assessmentService;
        this.userService = userService;
        this.organizationService = organizationService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createMappingData(String message) {
        if (message.contains(Constants.ENROLLMENT_DATA)) {
            processingEnrollmentRequestData(message);
        } else if (message.contains(Constants.ASSESSMENT_DATA)) {
            processingAssessmentRequestData(message);
        }
    }

    /**
     * Converting the Enrollment Request Data to respective DTO
     *
     * @param message The message for which mapping data is to be created
     */
    private void processingEnrollmentRequestData(String message) {
        try {
            FhirEnrollmentRequestDto enrollmentRequestDto = mapper.readValue(message, FhirEnrollmentRequestDto.class);
            if (enrollmentRequestDto.getPatient().getId() != null) {
                saveMapping(Constants.PATIENT, enrollmentRequestDto.getPatientTrackId(),
                        enrollmentRequestDto.getPatient().getId());
                Logger.logInfo(Constants.PATIENT + Constants.REQUEST_SAVED_IN_FHIR_MAPPING_TABLE);
            }
            if (enrollmentRequestDto.getBpLog() != null && enrollmentRequestDto.getBpLog().getId() != null) {
                saveMapping(Constants.BP_LOG, enrollmentRequestDto.getPatientTrackId(),
                        enrollmentRequestDto.getBpLog().getId());
                Logger.logInfo(Constants.BP_LOG + Constants.REQUEST_SAVED_IN_FHIR_MAPPING_TABLE);
            }
            if (enrollmentRequestDto.getGlucoseLog() != null && enrollmentRequestDto.getGlucoseLog().getId() != null) {
                saveMapping(Constants.GLUCOSE_LOG, enrollmentRequestDto.getPatientTrackId(),
                        enrollmentRequestDto.getGlucoseLog().getId());
                Logger.logInfo(Constants.GLUCOSE_LOG + Constants.REQUEST_SAVED_IN_FHIR_MAPPING_TABLE);
            }
        } catch (JsonProcessingException exception) {
            Logger.logError(ErrorConstants.ISSUE_CONVERTING_REQUEST_TO_REQUEST_DTO, exception);
            throw new BadRequestException(20004);
        }

    }

    /**
     * Converting the Assessment Request Data to respective DTO
     *
     * @param message The message for which mapping data is to be created
     */
    private void processingAssessmentRequestData(String message) {
        try {
            FhirAssessmentRequestDto assessmentRequestDto = mapper.readValue(message, FhirAssessmentRequestDto.class);
            if (assessmentRequestDto.getBpLog() != null && assessmentRequestDto.getBpLog().getId() != null) {
                saveMapping(Constants.BP_LOG, assessmentRequestDto.getPatientTrackId(),
                        assessmentRequestDto.getBpLog().getId());
                Logger.logInfo(Constants.BP_LOG + Constants.REQUEST_SAVED_IN_FHIR_MAPPING_TABLE);
            }
            if (assessmentRequestDto.getGlucoseLog() != null && assessmentRequestDto.getGlucoseLog().getId() != null) {
                saveMapping(Constants.GLUCOSE_LOG, assessmentRequestDto.getPatientTrackId(),
                        assessmentRequestDto.getGlucoseLog().getId());
                Logger.logInfo(Constants.GLUCOSE_LOG + Constants.REQUEST_SAVED_IN_FHIR_MAPPING_TABLE);
            }
        } catch (JsonProcessingException exception) {
            Logger.logError(ErrorConstants.ISSUE_CONVERTING_REQUEST_TO_REQUEST_DTO, exception);
            throw new BadRequestException(20004);
        }
    }

    /**
     * Save the initial details of Message service in FHIR Listener DB
     *
     * @param module   Spice Module(Patient, BpLog, GlucoseLog)
     * @param spiceId  Spice Patient Tracker Id
     * @param moduleId Spice Module Id
     */
    private void saveMapping(String module, Long spiceId, Long moduleId) {
        SpiceFhirMapping spiceFhirMapping = new SpiceFhirMapping();
        spiceFhirMapping.setSpiceId(Math.toIntExact(spiceId));
        spiceFhirMapping.setStatus(Constants.INITIAL);
        spiceFhirMapping.setReason(Constants.DATA_PROCESSED_TO_FHIR_ADAPTER);
        spiceFhirMapping.setSpiceModule(module);
        spiceFhirMapping.setSpiceModuleId(moduleId);
        spiceFhirMappingRepository.save(spiceFhirMapping);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String convertMessageToObject(String message) {
        try {
            if (message.contains(Constants.ENROLLMENT_DATA)) {
                FhirEnrollmentRequestDto fhirEnrollmentRequestDto = mapper.readValue(message,
                        FhirEnrollmentRequestDto.class);
                enrollmentService.createFhirPatient(fhirEnrollmentRequestDto);
                return Constants.MESSAGE_SENT_TO_HAPI_SERVER_SUCCESSFULLY;

            } else if (message.contains(Constants.ASSESSMENT_DATA)) {
                FhirAssessmentRequestDto assessmentRequest = mapper.readValue(message, FhirAssessmentRequestDto.class);
                if (null != assessmentRequest.getBpLog() || null != assessmentRequest.getGlucoseLog()) {
                    assessmentService.createFhirObservation(assessmentRequest, assessmentRequest.getPatientTrackId());
                }
                return Constants.MESSAGE_SENT_TO_HAPI_SERVER_SUCCESSFULLY;

            } else if (message.contains(Constants.USER_DATA)) {
                processingUserRequestData(message);
                return Constants.MESSAGE_SENT_TO_HAPI_SERVER_SUCCESSFULLY;

            } else if (message.contains(Constants.SITE_DATA)) {
                processingSiteRequestData(message);
                return Constants.MESSAGE_SENT_TO_HAPI_SERVER_SUCCESSFULLY;

            } else if (message.contains(Constants.PATIENT_EDIT)) {
                PatientUpdateRequestDTO updateRequestDTO = mapper.readValue(message, PatientUpdateRequestDTO.class);
                IBaseResource response = enrollmentService.updatePatientDetails(updateRequestDTO);
                return Constants.MESSAGE_UPDATED_SUCCESSFULLY + response.getIdElement();

            } else {
                throw new BadRequestException(20015);
            }

        } catch (JsonProcessingException exception) {
            Logger.logError(ErrorConstants.ISSUE_CONVERTING_REQUEST_TO_REQUEST_DTO, exception);
            throw new BadRequestException(20004);
        }
    }


    /**
     * Converting the User Request Data to respective DTO
     *
     * @param message The message for which mapping data is to be created
     */
    private void processingUserRequestData(String message) {
        try {
            FhirUserRequestDTO fhirUserRequestDto = mapper.readValue(message, FhirUserRequestDTO.class);
            List<FhirUserDTO> fhirUsers = fhirUserRequestDto.getUsers();
            fhirUsers.stream().forEach(fhirUserDTO -> {
                userService.createAndMapFhirPractitioner(fhirUserDTO);
            });

        } catch (JsonProcessingException exception) {
            Logger.logError(ErrorConstants.ISSUE_CONVERTING_REQUEST_TO_REQUEST_DTO, exception);
            throw new BadRequestException(20004);
        }
    }

    /**
     * Converting the Site Request Data to respective DTO
     *
     * @param message The message for which mapping data is to be created
     */
    private void processingSiteRequestData(String message) {
        try {
            FhirOrganizationRequestDto fhirOrganizationRequestDto = mapper.readValue(message,
                    FhirOrganizationRequestDto.class);
            organizationService.createAndMapFhirOrganization(fhirOrganizationRequestDto);

        } catch (JsonProcessingException exception) {
            Logger.logError(ErrorConstants.ISSUE_CONVERTING_REQUEST_TO_REQUEST_DTO, exception);
            throw new BadRequestException(20004);
        }
    }

}

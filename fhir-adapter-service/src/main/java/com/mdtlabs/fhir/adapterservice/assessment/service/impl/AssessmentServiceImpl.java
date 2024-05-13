package com.mdtlabs.fhir.adapterservice.assessment.service.impl;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.exceptions.FhirClientConnectionException;
import com.mdtlabs.fhir.adapterservice.assessment.service.AssessmentService;
import com.mdtlabs.fhir.adapterservice.converter.AssessmentRequestConverter;
import com.mdtlabs.fhir.adapterservice.converter.AssessmentResponseConverter;
import com.mdtlabs.fhir.adapterservice.fhirclient.FhirClient;
import com.mdtlabs.fhir.adapterservice.util.UpdatePatientDetails;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.constants.ErrorConstants;
import com.mdtlabs.fhir.commonservice.common.constants.FhirConstants;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotAcceptableException;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotFoundException;
import com.mdtlabs.fhir.commonservice.common.exception.FhirValidation;
import com.mdtlabs.fhir.commonservice.common.exception.RequestTimeOutException;
import com.mdtlabs.fhir.commonservice.common.exception.UnauthorizedException;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;
import com.mdtlabs.fhir.commonservice.common.model.dto.AssessmentResponseDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirAssessmentRequestDto;
import com.mdtlabs.fhir.commonservice.common.model.entity.UserSpiceFhirMapping;
import com.mdtlabs.fhir.commonservice.common.repository.SpiceFhirMappingRepository;
import com.mdtlabs.fhir.commonservice.common.repository.UserSpiceFhirMappingRepository;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Observation;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service implementation for handling SPICE assessments and creating FHIR observations.
 * Author: Shrikanth
 * Created on: February 27, 2024
 */
@Service
public class AssessmentServiceImpl implements AssessmentService {
    private final FhirClient fhirClient;
    private final SpiceFhirMappingRepository spiceFhirMappingRepository;
    private final UserSpiceFhirMappingRepository userSpiceFhirMappingRepository;
    private final UpdatePatientDetails updatePatientDetails;
    private final AssessmentRequestConverter assessmentRequestConverter;
    private final AssessmentResponseConverter assessmentResponseConverter;

    public AssessmentServiceImpl(FhirClient fhirClient,
                                 SpiceFhirMappingRepository spiceFhirMappingRepository,
                                 UserSpiceFhirMappingRepository userSpiceFhirMappingRepository,
                                 UpdatePatientDetails updatePatientDetails,
                                 AssessmentRequestConverter assessmentRequestConverter,
                                 AssessmentResponseConverter assessmentResponseConverter) {
        this.fhirClient = fhirClient;
        this.spiceFhirMappingRepository = spiceFhirMappingRepository;
        this.userSpiceFhirMappingRepository = userSpiceFhirMappingRepository;
        this.updatePatientDetails = updatePatientDetails;
        this.assessmentRequestConverter = assessmentRequestConverter;
        this.assessmentResponseConverter = assessmentResponseConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AssessmentResponseDTO createFhirObservation(FhirAssessmentRequestDto assessmentRequestDTO,
                                                       Long spicePatientTrackId) {
        try {
            if (null != assessmentRequestDTO.getBpLog() || null != assessmentRequestDTO.getGlucoseLog()) {
                return createAssessmentObservation(assessmentRequestDTO, spicePatientTrackId);
            } else {
                Logger.logError(ErrorConstants.ASSESSMENT_RESOURCE_VALIDATION_FAILED);
                throw new FhirValidation(ErrorConstants.ASSESSMENT_RESOURCE_VALIDATION_FAILED);
            }
        } catch (RestClientException | DataNotFoundException | DataNotAcceptableException | UnauthorizedException |
                 FhirValidation | RequestTimeOutException customException) {
            Logger.logError(ErrorConstants.ASSESSMENT_DETAILS_NOT_SAVED_IN_FHIR, customException.getMessage());
            updatePatientDetails.updateFailureStatus(ErrorConstants.PATIENT_NOT_ENROLLED_IN_FHIR,
                    assessmentRequestDTO.getPatientTrackId());
            throw customException;
        } catch (Exception exception) {
            Logger.logError(ErrorConstants.ASSESSMENT_DETAILS_NOT_SAVED_IN_FHIR, exception);
            updatePatientDetails.updateFailureStatus(ErrorConstants.ASSESSMENT_DETAILS_NOT_SAVED_IN_FHIR,
                    assessmentRequestDTO.getPatientTrackId());
            throw exception;
        }
    }

    /**
     * Creates an assessment observation based on the provided assessment request DTO.
     *
     * @param assessmentRequestDTO The assessment request DTO.
     * @param spicePatientTrackId  The SPICE patient track ID.
     * @return The assessment response DTO.
     */
    private AssessmentResponseDTO createAssessmentObservation(FhirAssessmentRequestDto assessmentRequestDTO,
                                                              Long spicePatientTrackId) {
        try {
            FhirContext fhirContext = FhirContext.forR4();

            Map<Long, Long> practitionerIdMap = getPractitionerIdMap(assessmentRequestDTO);
            Long fhirPatientId = (spiceFhirMappingRepository.getFhirPatientId(spicePatientTrackId, Constants.PATIENT,
                    Constants.PATIENT, Constants.PROCESSED)).orElseThrow(() -> new DataNotFoundException(1003));
            Logger.logInfo(Constants.FHIR_PATIENT_ID + fhirPatientId);
            Bundle bundle = fhirContext.newJsonParser().parseResource(Bundle.class,
                    fhirClient.sendBundleToHapiServer(createFhirBundle(assessmentRequestDTO,
                            practitionerIdMap, fhirPatientId)));
            Logger.logInfo(Constants.ASSESSMENT_DETAILS_SAVED_IN_HAPI_SERVER, bundle);
            return processBundleEntries(bundle, assessmentRequestDTO);
        } catch (FhirClientConnectionException fhirClientConnectionException) {
            Logger.logError(fhirClientConnectionException.getMessage());
            throw new RequestTimeOutException(20010, fhirClientConnectionException.getMessage());
        } catch (RestClientException restClientException) {
            if (restClientException.getMessage().contains(ErrorConstants.INVALID_API_KEY)) {
                throw new UnauthorizedException(20011, ErrorConstants.INVALID_API_KEY);
            } else {
                throw restClientException;
            }
        }
    }

    /**
     * Creates a FHIR Bundle based on the provided assessment request DTO, patient, and practitioner IDs.
     *
     * @param assessmentRequestDTO The assessment request DTO.
     * @param practitionerIdMap    The map of practitioner IDs.
     * @param spicePatientTrackId  The SPICE patient track ID.
     * @return The created FHIR Bundle.
     */
    private Bundle createFhirBundle(FhirAssessmentRequestDto assessmentRequestDTO,
                                    Map<Long, Long> practitionerIdMap, Long spicePatientTrackId) {
        return assessmentRequestConverter.createFhirBundle(assessmentRequestDTO,
                String.valueOf(spicePatientTrackId),
                practitionerIdMap.get(assessmentRequestDTO.getUpdatedBy()));
    }

    /**
     * Processes the entries in the provided FHIR Bundle, updating FHIR mappings accordingly.
     *
     * @param bundle               The FHIR Bundle to process.
     * @param assessmentRequestDTO The original AssessmentRequestDTO.
     * @return The resulting AssessmentResponseDTO.
     */
    private AssessmentResponseDTO processBundleEntries(Bundle bundle, FhirAssessmentRequestDto assessmentRequestDTO) {

        AssessmentResponseDTO assessmentResponseDTO = new AssessmentResponseDTO();
        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {

            if (entry.getResponse().getLocation().contains(FhirConstants.OBSERVATION)) {
                assessmentResponseDTO = updateObservation(assessmentRequestDTO, entry.getResponse().getLocation(),
                        assessmentResponseDTO);
            } else {
                Logger.logError(ErrorConstants.RESOURCE_CATEGORY_NOT_OBSERVATION);
                throw new DataNotAcceptableException(1001);
            }
        }
        Logger.logInfo(assessmentResponseDTO.toString());
        return assessmentResponseDTO;
    }

    /**
     * Updates Observation and the corresponding FHIR mapping based on the provided entry information.
     *
     * @param assessmentRequestDTO  The original AssessmentResponseDTO.
     * @param resourceUrl           The BundleEntryComponent containing information about the Observation.
     * @param assessmentResponseDTO The resulting AssessmentResponseDTO.
     * @return The resulting AssessmentResponseDTO.
     */
    private AssessmentResponseDTO updateObservation(FhirAssessmentRequestDto assessmentRequestDTO, String resourceUrl,
                                                    AssessmentResponseDTO assessmentResponseDTO) {
        try {
            Observation observation = fhirClient.getResourceDetails(resourceUrl, new Observation());
            if (Objects.equals(observation.getCode().getText(), FhirConstants.BLOOD_PRESSURE)
                    || Objects.equals(observation.getCode().getText(), FhirConstants.BLOOD_GLUCOSE)) {
                Long assessmentLogId;
                String assessmentLogType;
                if (Objects.equals(observation.getCode().getText(), FhirConstants.BLOOD_PRESSURE)) {
                    assessmentLogId = assessmentRequestDTO.getBpLog().getId();
                    assessmentLogType = FhirConstants.BP_LOG;
                } else {
                    assessmentLogId = assessmentRequestDTO.getGlucoseLog().getId();
                    assessmentLogType = FhirConstants.GLUCOSE_LOG;
                }
                updatePatientDetails.updateFhirMapping(assessmentLogId, assessmentLogType, observation,
                        assessmentRequestDTO.getPatientTrackId());
                assessmentResponseDTO = assessmentResponseConverter
                        .convertObservationToAssessmentResponseDTO(observation, assessmentResponseDTO);
            } else {
                Logger.logError(ErrorConstants.INVALID_RESOURCE_CATEGORY);
                throw new DataNotAcceptableException(1001);
            }
            return assessmentResponseDTO;
        } catch (FhirClientConnectionException fhirClientConnectionException) {
            Logger.logError(fhirClientConnectionException.getMessage());
            throw new RequestTimeOutException(20010, fhirClientConnectionException.getMessage());
        }
    }

    /**
     * Retrieves a map of user IDs to FHIR practitioner IDs.
     *
     * @param assessmentRequestDTO The assessment request DTO.
     * @return The map of user IDs to FHIR practitioner IDs.
     */
    private Map<Long, Long> getPractitionerIdMap(FhirAssessmentRequestDto assessmentRequestDTO) {
        List<Long> userIds = Stream.of(assessmentRequestDTO.getCreatedBy(),
                assessmentRequestDTO.getUpdatedBy()).toList();
        List<UserSpiceFhirMapping> userMappings = userSpiceFhirMappingRepository.findAllBySpiceUserIdIn(userIds);
        if (userMappings != null && !userMappings.isEmpty()) {
            return userMappings.stream()
                    .collect(Collectors.toMap(UserSpiceFhirMapping::getSpiceUserId,
                            UserSpiceFhirMapping::getFhirPractitionerId));
        } else {
            Logger.logError(ErrorConstants.USER_MAPPINGS_NOT_FOUND, userIds);
            throw new DataNotFoundException(1005);
        }
    }
}
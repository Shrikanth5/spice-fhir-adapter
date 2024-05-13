package com.mdtlabs.fhir.adapterservice.enrollment.service.impl;

import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.exceptions.FhirClientConnectionException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdtlabs.fhir.adapterservice.converter.EnrollmentRequestConverter;
import com.mdtlabs.fhir.adapterservice.converter.EnrollmentResponseConverter;
import com.mdtlabs.fhir.adapterservice.converter.PatientConverter;
import com.mdtlabs.fhir.adapterservice.enrollment.service.EnrollmentService;
import com.mdtlabs.fhir.adapterservice.fhirclient.FhirClient;
import com.mdtlabs.fhir.adapterservice.util.UpdatePatientDetails;
import com.mdtlabs.fhir.commonservice.common.DataConflictException;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.constants.ErrorConstants;
import com.mdtlabs.fhir.commonservice.common.constants.FhirConstants;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotAcceptableException;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotFoundException;
import com.mdtlabs.fhir.commonservice.common.exception.FhirValidation;
import com.mdtlabs.fhir.commonservice.common.exception.RequestTimeOutException;
import com.mdtlabs.fhir.commonservice.common.exception.UnauthorizedException;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;
import com.mdtlabs.fhir.commonservice.common.model.dto.EnrollmentResponseDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirEnrollmentRequestDto;
import com.mdtlabs.fhir.commonservice.common.model.dto.PatientDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.PatientUpdateRequestDTO;
import com.mdtlabs.fhir.commonservice.common.model.entity.SpiceFhirMapping;
import com.mdtlabs.fhir.commonservice.common.model.entity.UserSpiceFhirMapping;
import com.mdtlabs.fhir.commonservice.common.repository.SiteSpiceFhirMappingRepository;
import com.mdtlabs.fhir.commonservice.common.repository.SpiceFhirMappingRepository;
import com.mdtlabs.fhir.commonservice.common.repository.UserSpiceFhirMappingRepository;
import jakarta.validation.ValidationException;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service implementation for managing enrollment patient-related operations
 * <p>
 * Author: Charan
 * <p>
 * Created on: February 27, 2024
 */
@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    private final FhirClient fhirClient;
    private final SpiceFhirMappingRepository spiceFhirMappingRepository;
    private final UserSpiceFhirMappingRepository userSpiceFhirMappingRepository;
    private final SiteSpiceFhirMappingRepository siteSpiceFhirMappingRepository;
    private final UpdatePatientDetails updatePatientDetails;
    private final EnrollmentRequestConverter enrollmentRequestConverter;
    private final EnrollmentResponseConverter enrollmentResponseConverter;
    private final PatientConverter patientConverter;
    private final Patient patient = new Patient();

    public EnrollmentServiceImpl(FhirClient fhirClient,
                                 SpiceFhirMappingRepository spiceFhirMappingRepository,
                                 UserSpiceFhirMappingRepository userSpiceFhirMappingRepository,
                                 SiteSpiceFhirMappingRepository siteSpiceFhirMappingRepository,
                                 UpdatePatientDetails updatePatientDetails,
                                 EnrollmentRequestConverter enrollmentRequestConverter,
                                 EnrollmentResponseConverter enrollmentResponseConverter,
                                 PatientConverter patientConverter) {
        this.fhirClient = fhirClient;
        this.spiceFhirMappingRepository = spiceFhirMappingRepository;
        this.userSpiceFhirMappingRepository = userSpiceFhirMappingRepository;
        this.siteSpiceFhirMappingRepository = siteSpiceFhirMappingRepository;
        this.updatePatientDetails = updatePatientDetails;
        this.enrollmentRequestConverter = enrollmentRequestConverter;
        this.enrollmentResponseConverter = enrollmentResponseConverter;
        this.patientConverter = patientConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public EnrollmentResponseDTO createFhirPatient(FhirEnrollmentRequestDto enrollmentRequestDTO) {
        try {
            SpiceFhirMapping fhirMapping = duplicateFhirMappingCheck(enrollmentRequestDTO.getPatient().getId()
            );
            if (null == fhirMapping) {
                if (areMandatoryFieldsPresent(enrollmentRequestDTO.getPatient())) {
                    return createEnrolledPatientAndObservation(enrollmentRequestDTO);
                } else {
                    Logger.logError(ErrorConstants.RESOURCE_VALIDATION_FAILED);
                    throw new FhirValidation(ErrorConstants.RESOURCE_VALIDATION_FAILED);
                }
            } else {
                Logger.logError(ErrorConstants.DUPLICATE_PATIENT);
                throw new DataConflictException(1008, ErrorConstants.DUPLICATE_PATIENT);
            }
        } catch (RestClientException | DataNotFoundException | DataNotAcceptableException | UnauthorizedException |
                 DataConflictException | FhirValidation | RequestTimeOutException customException) {
            Logger.logError(ErrorConstants.PATIENT_NOT_ENROLLED_IN_FHIR_LOG, customException.getMessage());
            updatePatientDetails.updateFailureStatus(ErrorConstants.PATIENT_NOT_ENROLLED_IN_FHIR,
                    enrollmentRequestDTO.getPatientTrackId());
            throw customException;
        } catch (Exception exception) {
            Logger.logError(ErrorConstants.PATIENT_NOT_ENROLLED_IN_FHIR_LOG, exception);
            updatePatientDetails.updateFailureStatus(ErrorConstants.PATIENT_NOT_ENROLLED_IN_FHIR,
                    enrollmentRequestDTO.getPatientTrackId());
            throw exception;
        }
    }

    /**
     * Creates an EnrollmentResponseDTO by processing an EnrollmentRequestDTO, creating FHIR resources, and updating
     * FHIR mappings.
     *
     * @param enrollmentRequestDTO The EnrollmentRequestDTO to process.
     * @return The resulting EnrollmentResponseDTO.
     */
    private EnrollmentResponseDTO createEnrolledPatientAndObservation(FhirEnrollmentRequestDto enrollmentRequestDTO) {

        Map<Long, Long> practitionerIdMap = getPractitionerIdMap(enrollmentRequestDTO.getCreatedBy(),
                enrollmentRequestDTO.getUpdatedBy());
        Long organizationId = getOrganizationId(enrollmentRequestDTO.getPatient().getSiteId());
        try {

            String bundle = fhirClient.sendBundleToHapiServer(createFhirBundle(enrollmentRequestDTO, patient,
                    practitionerIdMap, organizationId));
            Logger.logInfo(Constants.PATIENT_DATA_SAVED_IN_DATABASE, bundle);

            return processBundleEntries(bundle, enrollmentRequestDTO);
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
     * Creates a FHIR Bundle based on the provided EnrollmentRequestDTO, Patient, and practitioner IDs.
     *
     * @param enrollmentRequestDTO The EnrollmentRequestDTO.
     * @param patient              The Patient instance.
     * @param practitionerIdMap    The map of practitioner IDs.
     * @param organizationId       Map of organizations
     * @return The created FHIR Bundle.
     */
    private Bundle createFhirBundle(FhirEnrollmentRequestDto enrollmentRequestDTO, Patient patient,
                                    Map<Long, Long> practitionerIdMap, Long organizationId) {
        return enrollmentRequestConverter.createFhirBundle(enrollmentRequestDTO,
                patient.getIdElement().getIdPart(),
                practitionerIdMap.get(enrollmentRequestDTO.getCreatedBy()),
                practitionerIdMap.get(enrollmentRequestDTO.getUpdatedBy()), organizationId);
    }

    /**
     * Processes the entries in the provided FHIR Bundle, updating FHIR mappings accordingly.
     *
     * @param responseJson         The FHIR Bundle to process.
     * @param enrollmentRequestDTO The original EnrollmentRequestDTO.
     * @return The updated EnrollmentResponseDTO.
     */
    private EnrollmentResponseDTO processBundleEntries(String responseJson,
                                                       FhirEnrollmentRequestDto enrollmentRequestDTO) {
        EnrollmentResponseDTO enrollmentResponseDTO = new EnrollmentResponseDTO();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseNode = objectMapper.readTree(responseJson);


            JsonNode firstEntry = responseNode.path(Constants.ENTRY).get(0);
            String location = firstEntry.path(Constants.RESPONSE).path(Constants.LOCATION).asText();


            String[] splitLocation = location.split(Constants.FORWARD_SLASH);
            String patientId = splitLocation[1];
            Bundle bundle = fhirClient.getPatientAndObservationDetails(patientId);

            for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {

                if (entry.getResource() instanceof Patient enrolledPatient) {
                    updatePatientDetails.updateFhirMapping(enrollmentRequestDTO.getPatient().getId(),
                            FhirConstants.PATIENT, enrolledPatient, enrollmentRequestDTO.getPatientTrackId());
                    enrollmentResponseDTO = enrollmentResponseConverter
                            .convertPatientToEnrollmentResponseDTO(enrolledPatient, enrollmentResponseDTO);
                } else if (entry.getResource() instanceof Observation) {
                    enrollmentResponseDTO = updateObservation(enrollmentRequestDTO, (Observation) entry.getResource(),
                            enrollmentResponseDTO);
                } else {
                    Logger.logError(ErrorConstants.RESOURCE_CATEGORY_NOT_PATIENT_OR_OBSERVATION);
                    throw new DataNotAcceptableException(1002);
                }
            }
        } catch (JsonProcessingException jsonProcessingException) {
            Logger.logError(ErrorConstants.UNABLE_TO_CONVERT_THE_FHIR_JSON_OBJECT + jsonProcessingException);
            throw new FhirValidation(20013, jsonProcessingException.getMessage());
        } catch (FhirClientConnectionException fhirClientConnectionException) {
            Logger.logError(fhirClientConnectionException.getMessage());
            throw new RequestTimeOutException(20010, fhirClientConnectionException.getMessage());
        }
        Logger.logInfo(enrollmentResponseDTO.toString());
        return enrollmentResponseDTO;
    }

    /**
     * Updates Observation and the corresponding FHIR mapping based on the provided entry information.
     *
     * @param enrollmentRequestDTO  The original EnrollmentRequestDTO.
     * @param assessmentObservation The BundleEntryComponent containing information about the Observation.
     * @param enrollmentResponseDTO The resulting EnrollmentResponseDTO.
     * @return The updated EnrollmentResponseDTO.
     */
    private EnrollmentResponseDTO updateObservation(FhirEnrollmentRequestDto enrollmentRequestDTO,
                                                    Observation assessmentObservation,
                                                    EnrollmentResponseDTO enrollmentResponseDTO) {
        if (Objects.equals(assessmentObservation.getCode().getText(), FhirConstants.BLOOD_PRESSURE)) {
            updatePatientDetails.updateFhirMapping(enrollmentRequestDTO.getBpLog().getId(),
                    FhirConstants.BP_LOG, assessmentObservation, enrollmentRequestDTO.getPatientTrackId());
            enrollmentResponseDTO = enrollmentResponseConverter
                    .convertObservationToEnrollmentResponseDTO(assessmentObservation, enrollmentResponseDTO);
        } else if (Objects.equals(assessmentObservation.getCode().getText(), FhirConstants.BLOOD_GLUCOSE)) {
            updatePatientDetails.updateFhirMapping(enrollmentRequestDTO.getGlucoseLog().getId(),
                    FhirConstants.GLUCOSE_LOG, assessmentObservation, enrollmentRequestDTO.getPatientTrackId());
            enrollmentResponseDTO = enrollmentResponseConverter
                    .convertObservationToEnrollmentResponseDTO(assessmentObservation, enrollmentResponseDTO);
        } else {
            Logger.logError(ErrorConstants.RESOURCE_CATEGORY_NOT_OBSERVATION);
            throw new DataNotAcceptableException(1001);
        }
        return enrollmentResponseDTO;
    }

    /**
     * Checks for duplicate FHIR mapping based on the provided parameters.
     *
     * @param spiceModuleId The ID of the Spice module.
     * @return {@code true} if a duplicate mapping exists, {@code false} otherwise.
     */
    private SpiceFhirMapping duplicateFhirMappingCheck(Long spiceModuleId) {

        return spiceFhirMappingRepository.
                findBySpiceModuleIdAndSpiceModuleAndStatusAndFhirResourceType(spiceModuleId,
                        FhirConstants.PATIENT, Constants.PROCESSED, FhirConstants.PATIENT);
    }

    /**
     * Checks if all mandatory fields are present in the given {@code FhirEnrollmentRequestDto}.
     *
     * @param patientDetails The {@code FhirEnrollmentRequestDto} to check.
     * @return {@code true} if all mandatory fields are present, {@code false} otherwise.
     */
    private boolean areMandatoryFieldsPresent(PatientDTO patientDetails) {
        return patientDetails.getId() != null &&
                patientDetails.getAge() != null &&
                patientDetails.getGender() != null &&
                patientDetails.getFirstName() != null &&
                patientDetails.getLastName() != null &&
                patientDetails.getPhoneNumber() != null &&
                patientDetails.getPhoneNumberCategory() != null &&
                patientDetails.getOccupation() != null &&
                patientDetails.getLevelOfEducation() != null;
    }

    /**
     * Retrieves a map of practitioner IDs based on the user IDs in the given {@code FhirEnrollmentRequestDto}.
     *
     * @param createdBy The ID of the practitioner who created the resources.
     * @param updatedBy The ID of the practitioner who updated the resources.
     * @return The map of practitioner IDs.
     */
    private Map<Long, Long> getPractitionerIdMap(Long createdBy, Long updatedBy) {
        List<Long> userIds = List.of(createdBy, updatedBy);
        List<UserSpiceFhirMapping> userMappings = userSpiceFhirMappingRepository.findAllBySpiceUserIdIn(userIds);
        if (userMappings != null && !userMappings.isEmpty()) {
            return userMappings.stream()
                    .collect(Collectors.toMap(UserSpiceFhirMapping::getSpiceUserId,
                            UserSpiceFhirMapping::getFhirPractitionerId));
        } else {
            Logger.logError(ErrorConstants.USER_MAPPINGS_NOT_FOUND, userIds);
            throw new DataNotFoundException(1005, ErrorConstants.USER_MAPPINGS_NOT_FOUND);
        }
    }

    private Long getOrganizationId(Long siteId) {
        if (siteId == null) {
            throw new DataNotFoundException(1009, ErrorConstants.SITE_ID_NULL);
        }
        try {
            return siteSpiceFhirMappingRepository.findFhirOrganizationIdBySiteSpiceId(siteId);
        } catch (EmptyResultDataAccessException ex) {
            Logger.logInfo(Constants.NO_SITE_MAPPINGS_FOUND);
            throw new DataNotFoundException(1006);
        }
    }

    /**
     * Update the existing patient details.
     *
     * @param requestDTO The {@link PatientUpdateRequestDTO} containing information for updating FHIR patient.
     * @return IBaseResource
     */
    public IBaseResource updatePatientDetails(PatientUpdateRequestDTO requestDTO) {
        try {
            SpiceFhirMapping fhirMapping = duplicateFhirMappingCheck(requestDTO.getPatientDTO().getId()
            );
            if (null != fhirMapping) {
                long id = fhirMapping.getFhirId();
                if (areMandatoryFieldsPresent(requestDTO.getPatientDTO())) {

                    return updatePatient(requestDTO, id).getResource();
                } else {
                    throw new ValidationException(ErrorConstants.RESOURCE_VALIDATION_FAILED);
                }
            } else {
                throw new ValidationException(ErrorConstants.PATIENT_NOT_ENROLLED_IN_FHIR);
            }
        } catch (Exception exception) {
            Logger.logError(exception);
            throw exception;
        }
    }

    private MethodOutcome updatePatient(PatientUpdateRequestDTO requestDTO, long id) {
        Map<Long, Long> practitionerIdMap = getPractitionerIdMap(requestDTO.getPatientDTO().getCreatedBy(),
                requestDTO.getPatientDTO().getUpdatedBy());
        Long organizationId = getOrganizationId(requestDTO.getPatientDTO().getSiteId());
        Patient fhirPatient = patientConverter.
                convertPatientToFhirBundleEntity(requestDTO.getPatientDTO(), practitionerIdMap.get(requestDTO.getUpdatedBy()), organizationId);
        fhirPatient.setId(Constants.EMPTY + id);

        return fhirClient.updateResource(fhirPatient);

    }
}

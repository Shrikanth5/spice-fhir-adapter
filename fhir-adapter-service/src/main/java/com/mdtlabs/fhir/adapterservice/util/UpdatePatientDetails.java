package com.mdtlabs.fhir.adapterservice.util;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;
import com.mdtlabs.fhir.commonservice.common.model.entity.SpiceFhirMapping;
import com.mdtlabs.fhir.commonservice.common.repository.SpiceFhirMappingRepository;
import org.hl7.fhir.r4.model.Resource;
import org.springframework.stereotype.Service;

/**
 * Service class for updating patient details in FHIR.
 * <p>
 * Author: Shrikanth
 * <p>
 * Created on: February 27, 2024
 */
@Service
public class UpdatePatientDetails {
    private final SpiceFhirMappingRepository spiceFhirMappingRepository;

    public UpdatePatientDetails(SpiceFhirMappingRepository spiceFhirMappingRepository) {
        this.spiceFhirMappingRepository = spiceFhirMappingRepository;
    }

    /**
     * Updates the FHIR mapping information for a given Spice module and patient track, marking it as processed with the
     * corresponding FHIR resource details.
     *
     * @param spiceModuleId       The ID of the Spice module.
     * @param spiceModule         The name of the Spice module.
     * @param fhirResource        The FHIR resource to be associated with the mapping.
     * @param spicePatientTrackId The ID of the Spice patient track.
     * @throws IllegalArgumentException if the FHIR resource ID cannot be parsed to an integer.
     */
    public void updateFhirMapping(Long spiceModuleId, String spiceModule, Resource fhirResource,
                                  Long spicePatientTrackId) {
        SpiceFhirMapping fhirMappingData = spiceFhirMappingRepository.
                findBySpiceModuleIdAndSpiceModuleAndStatusAndSpiceId(spiceModuleId,
                        spiceModule, Constants.INITIAL, spicePatientTrackId);
        if (fhirMappingData != null) {
            fhirMappingData.setFhirId(Math.toIntExact(Long.parseLong(fhirResource.getIdElement().getIdPart())));
            fhirMappingData.setStatus(Constants.PROCESSED);
            fhirMappingData.setFhirResourceType(fhirResource.getResourceType().name());
            fhirMappingData.setReason(Constants.DATA_PROCESSED_TO_FHIR_SERVER);
            spiceFhirMappingRepository.save(fhirMappingData);
            Logger.logInfo(Constants.MESSAGE_SAVED_IN_FHIR_MAPPING_TABLE);
        }
    }

    /**
     * Updates the failure status for a given Spice ID with the provided failure message.
     *
     * @param failureMessage The message describing the failure.
     * @param spiceId        The ID of the Spice module associated with the failure.
     */
    public void updateFailureStatus(String failureMessage, Long spiceId) {
        int updatedResult = spiceFhirMappingRepository.updateEventStatus(spiceId,
                failureMessage);
        if (Constants.ZERO != updatedResult) {
            Logger.logInfo(Constants.FAILED_STATUS_UPDATED);
        }
    }
}

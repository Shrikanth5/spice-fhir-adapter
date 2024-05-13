package com.mdtlabs.fhir.commonservice.common.repository;

import com.mdtlabs.fhir.commonservice.common.constants.FhirConstants;
import com.mdtlabs.fhir.commonservice.common.model.entity.SpiceFhirMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SpiceFhirMappingRepository extends JpaRepository<SpiceFhirMapping, Long> {

    String UPDATE_EVENT_STATUS = "Update fhir_mapping set status = 'FAILED', " +
            "reason = :errorMessage where spice_id = :patientTrackId and status = 'INITIAL'";

    String GET_FHIR_PATIENT_ID = "Select fhir_id from fhir_mapping" +
            " where spice_id = :patientTrackId and spice_module = :spiceModule and " +
            "fhir_resource_type = :fhirType and status = :progressStatus";


    SpiceFhirMapping findBySpiceModuleIdAndSpiceModuleAndStatusAndSpiceId(Long spiceId, String spiceModule,
                                                                          String status, Long spicePatientTrackId);

    SpiceFhirMapping findBySpiceModuleIdAndSpiceModuleAndStatusAndFhirResourceType(Long spiceModuleId,
                                                                                   String spiceModule,
                                                                                   String status, String type);

    @Query(value = GET_FHIR_PATIENT_ID, nativeQuery = true)
    Optional<Long> getFhirPatientId(@Param(FhirConstants.SPICE_PATIENT_TRACK_ID) Long spicePatientTrackId,
                                    @Param(FhirConstants.SPICE_MODULE) String spiceModule,
                                    @Param(FhirConstants.FHIR_MODULE) String resourceType,
                                    @Param(FhirConstants.PROGRESS_STATUS) String progressStatus);

    @Modifying
    @Transactional
    @Query(value = UPDATE_EVENT_STATUS, nativeQuery = true)
    int updateEventStatus(@Param(FhirConstants.SPICE_PATIENT_TRACK_ID) Long spiceId,
                          @Param(FhirConstants.VALIDATION_OR_DUPLICATE_MESSAGE) String errorMessage);

}


package com.mdtlabs.fhir.fhirlistenerservice.repository;

import com.mdtlabs.fhir.commonservice.common.model.entity.SpiceMessageTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing SpiceMessageTracker entities.
 * <p>
 * Author: Akash Gopinath
 * Created on: February 12, 2024
 */
@Repository
public interface SpiceMessageTrackerRepository extends JpaRepository<SpiceMessageTracker, Long> {

    /**
     * Finds a list of SpiceMessageTracker entities by their deduplication ID.
     *
     * @param deduplicationId The deduplication ID for which to find SpiceMessageTracker entities.
     * @return A list of SpiceMessageTracker entities matching the given deduplication ID.
     * Author: Akash Gopinath
     * Created on: February 12, 2024
     */
    List<SpiceMessageTracker> findByDeduplicationId(String deduplicationId);
}

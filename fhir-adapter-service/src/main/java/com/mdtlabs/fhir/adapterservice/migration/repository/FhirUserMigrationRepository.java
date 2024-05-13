package com.mdtlabs.fhir.adapterservice.migration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mdtlabs.fhir.commonservice.common.model.entity.UserSpiceFhirMapping;

/**
 * The type Fhir user migration repository.
 * Author: Akash Gopinath
 * Created on: April 10, 2024
 */
@Repository
public interface FhirUserMigrationRepository extends JpaRepository<UserSpiceFhirMapping, Long> {

    @Query(value = "select spiceUserId from UserSpiceFhirMapping")
    List<Long> getAllSpiceUserIds();
}

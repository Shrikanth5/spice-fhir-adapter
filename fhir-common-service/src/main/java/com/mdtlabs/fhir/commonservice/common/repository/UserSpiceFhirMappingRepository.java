package com.mdtlabs.fhir.commonservice.common.repository;

import com.mdtlabs.fhir.commonservice.common.model.entity.UserSpiceFhirMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSpiceFhirMappingRepository extends JpaRepository<UserSpiceFhirMapping, Long> {

    List<UserSpiceFhirMapping> findAllBySpiceUserIdIn(Iterable<Long> userIds);
}

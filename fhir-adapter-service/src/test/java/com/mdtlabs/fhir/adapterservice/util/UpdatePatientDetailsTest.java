package com.mdtlabs.fhir.adapterservice.util;

import com.mdtlabs.fhir.commonservice.common.model.entity.SpiceFhirMapping;
import com.mdtlabs.fhir.commonservice.common.repository.SpiceFhirMappingRepository;
import org.hl7.fhir.r4.model.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UpdatePatientDetails.class})
@ExtendWith(SpringExtension.class)
class UpdatePatientDetailsTest {

    @MockBean
    private SpiceFhirMappingRepository spiceFhirMappingRepository;

    @MockBean
    private Resource fhirResource;

    @Autowired
    private UpdatePatientDetails updatePatientDetails;

    @Test
    void testUpdateFhirMapping() {
        Long spiceModuleId = 1L;
        String spiceModule = "Test Module";
        Long spicePatientTrackId = 2L;
        when(spiceFhirMappingRepository.findBySpiceModuleIdAndSpiceModuleAndStatusAndSpiceId(any(Long.class),
                any(String.class), any(String.class), any(Long.class))).thenReturn(new SpiceFhirMapping());
        when(fhirResource.getIdElement()).thenReturn(new org.hl7.fhir.r4.model.IdType("123"));
        when(fhirResource.getResourceType()).thenReturn(org.hl7.fhir.r4.model.ResourceType.Patient);
        updatePatientDetails.updateFhirMapping(spiceModuleId, spiceModule, fhirResource, spicePatientTrackId);
        verify(spiceFhirMappingRepository, times(1)).save(any(SpiceFhirMapping.class));
    }

    @Test
    void testUpdateFailureStatus() {
        Long spiceId = 1L;
        String failureMessage = "Test Failure Message";
        when(spiceFhirMappingRepository.updateEventStatus(any(Long.class), any(String.class))).thenReturn(1);
        updatePatientDetails.updateFailureStatus(failureMessage, spiceId);
        verify(spiceFhirMappingRepository, times(1)).updateEventStatus(anyLong(), anyString());
    }
}

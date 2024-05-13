package com.mdtlabs.fhir.adapterservice.converter;

import com.mdtlabs.fhir.adapterservice.utils.TestUtility;
import com.mdtlabs.fhir.commonservice.common.constants.TestConstants;
import com.mdtlabs.fhir.commonservice.common.model.dto.BpLogDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirEnrollmentRequestDto;
import com.mdtlabs.fhir.commonservice.common.model.dto.GlucoseLogDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.PatientDTO;
import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.r4.model.BooleanType;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {EnrollmentRequestConverter.class})
@ExtendWith(SpringExtension.class)
class EnrollmentRequestConverterTest {
    @MockBean
    private BloodGlucoseConverter bloodGlucoseConverter;

    @MockBean
    private BloodPressureConverter bloodPressureConverter;

    @Autowired
    private EnrollmentRequestConverter enrollmentRequestConverter;

    @MockBean
    private PatientConverter patientConverter;

    /**
     * Method under test:
     * {@link EnrollmentRequestConverter#createFhirBundle(FhirEnrollmentRequestDto, String, Long, Long, Long)}
     */
    @Test
    void testCreateFhirBundle() throws FHIRException {

        when(patientConverter.convertPatientToFhirBundleEntity(Mockito.any(), Mockito.<Long>any(),
                Mockito.<Long>any())).thenReturn(new Patient());
        when(bloodPressureConverter.convertBloodPressureObservationToFhirBundleEntity(Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.<Long>any(), Mockito.<Boolean>any(), Mockito.<Long>any()))
                .thenReturn(new Observation());
        when(bloodGlucoseConverter.convertBloodGlucoseObservationToFhirBundleEntity(Mockito.any(),
                Mockito.any(), Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(new Observation());
        GlucoseLogDTO glucoseLogDTO = new GlucoseLogDTO();
        FhirEnrollmentRequestDto enrollmentRequestDTO = TestUtility.getFHIREnrollmentRequest();
        glucoseLogDTO.setId(TestConstants.ONE_LONG);
        enrollmentRequestDTO.setGlucoseLog(glucoseLogDTO);

        Bundle actualCreateFhirBundleResult = enrollmentRequestConverter.createFhirBundle(enrollmentRequestDTO, "42", 1L,
                1L, 1L);
        BooleanType expectedDeceased = ((Patient) actualCreateFhirBundleResult.getEntryFirstRep().getResource())
                .getDeceasedBooleanType();
        assertSame(expectedDeceased,
                ((Patient) actualCreateFhirBundleResult.getEntryFirstRep().getResource()).getDeceased());
    }
}

package com.mdtlabs.fhir.adapterservice.converter;

import com.mdtlabs.fhir.adapterservice.utils.TestUtility;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotFoundException;
import com.mdtlabs.fhir.commonservice.common.model.dto.GlucoseLogDTO;
import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.r4.model.Observation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BloodGlucoseConverterTest {
    @InjectMocks
    BloodGlucoseConverter bloodGlucoseConverter;

    @Test
    void testConvertBloodGlucoseObservationToFhirBundleEntity() throws FHIRException {
        ReflectionTestUtils.setField(bloodGlucoseConverter, "countryId", 3L);
        GlucoseLogDTO glucoseLog = TestUtility.getFhirAssessmentRequest().getGlucoseLog();

        glucoseLog.setHba1c(2.0);

        Observation actualObservation = bloodGlucoseConverter.convertBloodGlucoseObservationToFhirBundleEntity(
                glucoseLog, "42", 1L, 3L);

        assertEquals(Observation.ObservationStatus.FINAL, actualObservation.getStatus());
        assertFalse(actualObservation.hasValueBooleanType());

        glucoseLog.setGlucoseType(Constants.FBS);
        assertThrows(DataNotFoundException.class, () -> bloodGlucoseConverter.convertBloodGlucoseObservationToFhirBundleEntity(
                glucoseLog, "42", null, 3L));

        glucoseLog.setGlucoseType(Constants.RBS);
        assertThrows(DataNotFoundException.class, () -> bloodGlucoseConverter.convertBloodGlucoseObservationToFhirBundleEntity(
                glucoseLog, "42", null, 3L));

        assertThrows(DataNotFoundException.class, () -> bloodGlucoseConverter.convertBloodGlucoseObservationToFhirBundleEntity(
                glucoseLog, null, null, 3L));


    }
}

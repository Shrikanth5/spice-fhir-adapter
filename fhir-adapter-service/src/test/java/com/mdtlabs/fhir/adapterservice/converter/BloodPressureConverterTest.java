package com.mdtlabs.fhir.adapterservice.converter;

import com.mdtlabs.fhir.adapterservice.utils.TestUtility;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotFoundException;
import com.mdtlabs.fhir.commonservice.common.exception.FhirValidation;
import com.mdtlabs.fhir.commonservice.common.model.dto.BpLogDTO;
import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.r4.model.BooleanType;
import org.hl7.fhir.r4.model.Observation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BloodPressureConverterTest {
    /**
     * Method under test:
     * {@link BloodPressureConverter#convertBloodPressureObservationToFhirBundleEntity(BpLogDTO, String, String, Long, Boolean, Long)}
     */
    @Test
    void testConvertBloodPressureObservationToFhirBundleEntity() {
        BloodPressureConverter bloodPressureConverter = new BloodPressureConverter();
        assertThrows(FhirValidation.class, () -> bloodPressureConverter.convertBloodPressureObservationToFhirBundleEntity(new BpLogDTO(), "42", "Request Type", 1L, true, 3L));
    }

    /**
     * Method under test:
     * {@link BloodPressureConverter#convertBloodPressureObservationToFhirBundleEntity(BpLogDTO, String, String, Long, Boolean, Long)}
     */
    @Test
    void bloodPressureFhirValidationExceptionTest() {
        BloodPressureConverter bloodPressureConverter = new BloodPressureConverter();

        BpLogDTO createBPLog = new BpLogDTO();
        createBPLog.setAvgSystolic(1007);

        assertThrows(FhirValidation.class, () -> bloodPressureConverter.convertBloodPressureObservationToFhirBundleEntity(createBPLog, "42", "Request Type", 1L, true, 3L));
    }
    /**
     * Method under test:
     * {@link BloodPressureConverter#convertBloodPressureObservationToFhirBundleEntity(BpLogDTO, String, String, Long, Boolean, Long)}
     */
    @Test
    void bloodPressureObservationToFhirBundleEntityValueTest() throws FHIRException {


        BloodPressureConverter bloodPressureConverter = new BloodPressureConverter();
        BpLogDTO createBPLog = TestUtility.getBpLog();

        Observation actualConvertBloodPressureObservationToFhirBundleEntityResult = bloodPressureConverter.convertBloodPressureObservationToFhirBundleEntity(createBPLog, "42", "Request Type", 1L, true, 3L);

        BooleanType expectedValue = actualConvertBloodPressureObservationToFhirBundleEntityResult.getValueBooleanType();
        assertSame(expectedValue, actualConvertBloodPressureObservationToFhirBundleEntityResult.getValue());
    }

    /**
     * Method under test:
     * {@link BloodPressureConverter#convertBloodPressureObservationToFhirBundleEntity(BpLogDTO, String, String, Long, Boolean, Long)}
     */
    @Test
    void bloodPressureObservationToFhirBundleEntityPatientIDNullTest() throws FHIRException {


        BloodPressureConverter bloodPressureConverter = new BloodPressureConverter();
        BpLogDTO createBPLog = TestUtility.getBpLog();

        Observation actualConvertBloodPressureObservationToFhirBundleEntityResult = bloodPressureConverter.convertBloodPressureObservationToFhirBundleEntity(createBPLog, null, "Request Type", 1L, true, 3L);

        BooleanType expectedValue = actualConvertBloodPressureObservationToFhirBundleEntityResult.getValueBooleanType();
        assertSame(expectedValue, actualConvertBloodPressureObservationToFhirBundleEntityResult.getValue());
    }



    /**
     * Method under test:
     * {@link BloodPressureConverter#convertBloodPressureObservationToFhirBundleEntity(BpLogDTO, String, String, Long, Boolean, Long)}
     */
    @Test
    void bloodPressureObservationToFhirBundleEntityDataNotFoundExceptionTest() {


        BloodPressureConverter bloodPressureConverter = new BloodPressureConverter();
        BpLogDTO createBPLog = TestUtility.getBpLog();

        assertThrows(DataNotFoundException.class, () -> bloodPressureConverter.convertBloodPressureObservationToFhirBundleEntity(createBPLog, "42", "Request Type", null, true, 3L));
    }

    /**
     * Method under test:
     * {@link BloodPressureConverter#convertBloodPressureObservationToFhirBundleEntity(BpLogDTO, String, String, Long, Boolean, Long)}
     */
    @Test
    void bloodPressureObservationToFhirBundleEntityPractitionerIdTest() throws FHIRException {


        BloodPressureConverter bloodPressureConverter = new BloodPressureConverter();
        BpLogDTO createBPLog = TestUtility.getBpLog();

        Observation actualConvertBloodPressureObservationToFhirBundleEntityResult = bloodPressureConverter.convertBloodPressureObservationToFhirBundleEntity(createBPLog, "42", "Request Type", 1L, true, null);
        BooleanType expectedValue = actualConvertBloodPressureObservationToFhirBundleEntityResult.getValueBooleanType();
        assertSame(expectedValue, actualConvertBloodPressureObservationToFhirBundleEntityResult.getValue());
    }
}

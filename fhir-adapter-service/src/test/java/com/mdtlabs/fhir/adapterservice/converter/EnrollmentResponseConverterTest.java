package com.mdtlabs.fhir.adapterservice.converter;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import com.mdtlabs.fhir.adapterservice.utils.TestUtility;
import com.mdtlabs.fhir.commonservice.common.constants.FhirConstants;
import com.mdtlabs.fhir.commonservice.common.model.dto.EnrollmentResponseDTO;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {EnrollmentResponseConverter.class})
@ExtendWith(SpringExtension.class)
class EnrollmentResponseConverterTest {
    @MockBean
    IParser parserMock;
    @Autowired
    private EnrollmentResponseConverter enrollmentResponseConverter;
    @MockBean
    private FhirContext fhirContext;

    /**
     * Method under test:
     * {@link EnrollmentResponseConverter#convertObservationToEnrollmentResponseDTO(Observation, EnrollmentResponseDTO)}
     */
    @Test
    void testConvertObservationToEnrollmentResponseDTO() {
        Observation resource = TestUtility.getBPObservation();

        EnrollmentResponseDTO enrollmentResponseDTO = new EnrollmentResponseDTO();

        when(fhirContext.newJsonParser()).thenReturn(parserMock);
        when(fhirContext.newJsonParser().encodeResourceToString(any())).thenReturn("Sample");
        resource.setCode(null);

        resource.getCode().setText(FhirConstants.BLOOD_PRESSURE);
        assertSame(enrollmentResponseDTO,
                enrollmentResponseConverter.convertObservationToEnrollmentResponseDTO(resource, enrollmentResponseDTO));

        resource.getCode().setText(FhirConstants.BLOOD_GLUCOSE);
        assertSame(enrollmentResponseDTO,
                enrollmentResponseConverter.convertObservationToEnrollmentResponseDTO(resource, enrollmentResponseDTO));
    }

    @Test
    void testConvertPatientToEnrollmentResponseDTO() {
        Observation resource = TestUtility.getBPObservation();

        EnrollmentResponseDTO enrollmentResponseDTO = new EnrollmentResponseDTO();

        when(fhirContext.newJsonParser()).thenReturn(parserMock);
        when(fhirContext.newJsonParser().encodeResourceToString(any())).thenReturn("Sample");
        resource.setCode(null);

        resource.getCode().setText(FhirConstants.BLOOD_PRESSURE);
        assertSame(enrollmentResponseDTO,
                enrollmentResponseConverter.convertPatientToEnrollmentResponseDTO(new Patient(), enrollmentResponseDTO));

    }
}

package com.mdtlabs.fhir.adapterservice.converter;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import com.mdtlabs.fhir.adapterservice.utils.TestUtility;
import com.mdtlabs.fhir.commonservice.common.model.dto.AssessmentResponseDTO;
import org.hl7.fhir.r4.model.Enumeration;
import org.hl7.fhir.r4.model.Factory;
import org.hl7.fhir.r4.model.Observation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {AssessmentResponseConverter.class})
@ExtendWith(SpringExtension.class)
class AssessmentResponseConverterTest {
    @MockBean
    IParser parserMock;
    @Autowired
    private AssessmentResponseConverter assessmentResponseConverter;
    @MockBean
    private FhirContext fhirContext;

    /**
     * Method under test:
     * {@link AssessmentResponseConverter#convertObservationToAssessmentResponseDTO(Observation, AssessmentResponseDTO)}
     */
    @Test
    void testConvertObservationToAssessmentResponseDTO() {
        Observation resource = TestUtility.getBPObservation();

        AssessmentResponseDTO assessmentResponseDTO = TestUtility.createAssessmentResponseDTO();


        when(fhirContext.newJsonParser()).thenReturn(parserMock);
        when(fhirContext.newJsonParser().encodeResourceToString(any())).thenReturn("Sample");

        assertSame(assessmentResponseDTO, assessmentResponseConverter.convertObservationToAssessmentResponseDTO(resource, assessmentResponseDTO));

        resource = TestUtility.getBGObservation();
        assertSame(assessmentResponseDTO, assessmentResponseConverter.convertObservationToAssessmentResponseDTO(resource, assessmentResponseDTO));
    }
}

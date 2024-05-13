package com.mdtlabs.fhir.adapterservice.converter;

import com.mdtlabs.fhir.adapterservice.utils.TestUtility;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirAssessmentRequestDto;
import org.hl7.fhir.r4.model.Bundle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ContextConfiguration(classes = {AssessmentRequestConverter.class})
@ExtendWith(SpringExtension.class)
class AssessmentRequestConverterTest {
    @Autowired
    private AssessmentRequestConverter assessmentRequestConverter;

    @MockBean
    private BloodPressureConverter bloodPressureConverter;
    @MockBean
    private BloodGlucoseConverter bloodGlucoseConverter;

    /**
     * Method under test:
     * {@link AssessmentRequestConverter#createFhirBundle(FhirAssessmentRequestDto, String, Long)}
     */
    @Test
    void testCreateFhirBundle() {

        FhirAssessmentRequestDto assessmentRequestDTO = TestUtility.getFhirAssessmentRequest();
        assessmentRequestDTO.setBpLog(TestUtility.getBPLogDTO());
        String fhirPatientId = "1L";
        Long updatedByPractitionerId = 1L;

        Bundle actualCreateFhirBundleResult = this.assessmentRequestConverter.createFhirBundle(assessmentRequestDTO,
                fhirPatientId, updatedByPractitionerId);

        assertNotNull(actualCreateFhirBundleResult, "The created FHIR Bundle should not be null");

    }
}

package com.mdtlabs.fhir.adapterservice.converter;

import ca.uhn.fhir.model.api.TemporalPrecisionEnum;
import com.mdtlabs.fhir.adapterservice.utils.TestUtility;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotFoundException;
import com.mdtlabs.fhir.commonservice.common.model.dto.PatientDTO;
import org.hl7.fhir.r4.model.BooleanType;
import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.DateType;
import org.hl7.fhir.r4.model.Enumeration;
import org.hl7.fhir.r4.model.Enumerations;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Reference;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {PatientConverter.class})
@ExtendWith(SpringExtension.class)
class PatientConverterTest {
    @Autowired
    private PatientConverter patientConverter;

    /**
     * Method under test:
     * {@link PatientConverter#convertPatientToFhirBundleEntity(PatientDTO, Long, Long)}
     */
    @Test
    void testConvertPatientToFhirBundleEntity() {
        // Arrange
        PatientDTO patientDTO = TestUtility.getPatientDTO();
        // Act
        Patient actualConvertPatientToFhirBundleEntityResult = patientConverter.convertPatientToFhirBundleEntity(patientDTO,
                1L, 1L);
        //then
        Identifier identifierFirstRep = actualConvertPatientToFhirBundleEntityResult.getIdentifierFirstRep();
        assertEquals("42", identifierFirstRep.getValue());
        ContactPoint telecomFirstRep = actualConvertPatientToFhirBundleEntityResult.getTelecomFirstRep();
        assertEquals("6625550144", telecomFirstRep.getValue());
        HumanName nameFirstRep = actualConvertPatientToFhirBundleEntityResult.getNameFirstRep();
        assertEquals("Doe", nameFirstRep.getFamily());
        assertEquals("Initial", nameFirstRep.getPrefixAsSingleString());
        assertEquals("Jane Middle Name", nameFirstRep.getGivenAsSingleString());
        Reference managingOrganization = actualConvertPatientToFhirBundleEntityResult.getManagingOrganization();
        assertEquals("Organization/1", managingOrganization.getReference());
        Reference generalPractitionerFirstRep = actualConvertPatientToFhirBundleEntityResult
                .getGeneralPractitionerFirstRep();
        assertEquals("Practitioner/1", generalPractitionerFirstRep.getReference());
        Enumeration<Enumerations.AdministrativeGender> genderElement = actualConvertPatientToFhirBundleEntityResult
                .getGenderElement();
        assertEquals("http://hl7.org/fhir/administrative-gender", genderElement.toSystem());
        assertEquals("http://mdtlabs.com/Patient", identifierFirstRep.getSystem());
        assertEquals("other", genderElement.getCode());
        DateType birthDateElement = actualConvertPatientToFhirBundleEntityResult.getBirthDateElement();
        assertEquals(0, birthDateElement.getMillis().intValue());
        assertEquals(0L, birthDateElement.getNanos().longValue());
        assertEquals(1, actualConvertPatientToFhirBundleEntityResult.getGeneralPractitioner().size());
        assertEquals(1, actualConvertPatientToFhirBundleEntityResult.getName().size());
        assertEquals(1, actualConvertPatientToFhirBundleEntityResult.getTelecom().size());
        assertEquals(2, actualConvertPatientToFhirBundleEntityResult.getIdentifier().size());
        assertEquals(TemporalPrecisionEnum.DAY, birthDateElement.getPrecision());
        assertEquals(ContactPoint.ContactPointSystem.PHONE, telecomFirstRep.getSystem());
        assertEquals(Enumerations.AdministrativeGender.OTHER, actualConvertPatientToFhirBundleEntityResult.getGender());
        assertEquals(Enumerations.AdministrativeGender.OTHER, genderElement.getValue());
        assertFalse(birthDateElement.isTimeZoneZulu());
        assertTrue(telecomFirstRep.hasSystem());
        assertTrue(telecomFirstRep.hasValue());
        assertTrue(nameFirstRep.hasFamily());
        assertTrue(nameFirstRep.hasGiven());
        assertTrue(nameFirstRep.hasPrefix());
        assertTrue(identifierFirstRep.hasSystem());
        assertTrue(identifierFirstRep.hasValue());
        assertTrue(actualConvertPatientToFhirBundleEntityResult.hasActive());
        assertTrue(actualConvertPatientToFhirBundleEntityResult.hasBirthDate());
        assertTrue(actualConvertPatientToFhirBundleEntityResult.hasManagingOrganization());
        BooleanType activeElement = actualConvertPatientToFhirBundleEntityResult.getActiveElement();
        assertTrue(activeElement.getValue());
        assertTrue(birthDateElement.hasPrimitiveValue());
        assertTrue(generalPractitionerFirstRep.hasReference());
        assertTrue(managingOrganization.hasReference());
        String expectedValueAsString = Boolean.TRUE.toString();
        assertEquals(expectedValueAsString, activeElement.getValueAsString());

        assertThrows(DataNotFoundException.class, () -> patientConverter.convertPatientToFhirBundleEntity(patientDTO,
                null, 1L));
    }
}

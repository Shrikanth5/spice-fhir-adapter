package com.mdtlabs.fhir.adapterservice.converter;

import com.mdtlabs.fhir.adapterservice.utils.TestUtility;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirUserDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.PractitionerDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.PractitionerRoleDTO;
import org.hl7.fhir.r4.model.BooleanType;
import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.Enumerations;
import org.hl7.fhir.r4.model.Factory;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Practitioner;
import org.hl7.fhir.r4.model.PractitionerRole;
import org.hl7.fhir.r4.model.StringType;
import org.hl7.fhir.r4.model.UriType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {PractitionerConverter.class})
@ExtendWith(SpringExtension.class)
class PractitionerConverterTest {
    @Autowired
    private PractitionerConverter practitionerConverter;

    /**
     * Method under test:
     * {@link PractitionerConverter#convertUserToFhirEntity(FhirUserDTO)}
     */
    @Test
    void testConvertUserToFhirEntity() {
        // Arrange
        FhirUserDTO userDTO = TestUtility.createMockFhirUserRequestDto();

        // Act
        Practitioner actualConvertUserToFhirEntityResult = practitionerConverter.convertUserToFhirEntity(userDTO);

        Identifier identifierFirstRep = actualConvertUserToFhirEntityResult.getIdentifierFirstRep();
        assertEquals("1", identifierFirstRep.getValue());
        ContactPoint telecomFirstRep = actualConvertUserToFhirEntityResult.getTelecomFirstRep();
        assertEquals("6625550144", telecomFirstRep.getValue());
        HumanName nameFirstRep = actualConvertUserToFhirEntityResult.getNameFirstRep();
        assertEquals("Doe", nameFirstRep.getFamily());
        assertEquals("Jane", nameFirstRep.getGivenAsSingleString());
        assertEquals("http://mdtlabs.com/Practitioner", identifierFirstRep.getSystem());
        UriType systemElement = identifierFirstRep.getSystemElement();
        assertEquals("http://mdtlabs.com/Practitioner", systemElement.getValue());
        assertEquals("http://mdtlabs.com/Practitioner", systemElement.getValueAsString());
        assertEquals(1, actualConvertUserToFhirEntityResult.getIdentifier().size());
        assertEquals(1, actualConvertUserToFhirEntityResult.getName().size());
        assertEquals(2, actualConvertUserToFhirEntityResult.getTelecom().size());
        assertEquals(ContactPoint.ContactPointSystem.PHONE, telecomFirstRep.getSystem());
        assertTrue(telecomFirstRep.hasSystem());
        assertTrue(telecomFirstRep.hasValue());
        assertTrue(nameFirstRep.hasFamily());
        assertTrue(nameFirstRep.hasGiven());
        assertTrue(identifierFirstRep.hasValue());
        assertTrue(actualConvertUserToFhirEntityResult.hasActive());
    }

    /**
     * Method under test:
     * {@link PractitionerConverter#convertPractitionerToUserDto(Practitioner)}
     */
    @Test
    void testConvertPractitionerToUserDto() {
        // Arrange
        Identifier identifier = mock(Identifier.class);
        when(identifier.getValue()).thenReturn("42");

        HumanName humanName = new HumanName();
        humanName.addGivenElement();
        Practitioner practitioner = mock(Practitioner.class);

        when(practitioner.getTelecom()).thenReturn(new ArrayList<>());
        when(practitioner.getGender()).thenReturn(Enumerations.AdministrativeGender.MALE);
        when(practitioner.getTelecomFirstRep())
                .thenReturn(Factory.newContactPoint(ContactPoint.ContactPointSystem.PHONE, "42"));
        when(practitioner.getActive()).thenReturn(true);
        when(practitioner.getNameFirstRep()).thenReturn(humanName);
        when(practitioner.getIdentifierFirstRep()).thenReturn(identifier);
        when(practitioner.getIdElement()).thenReturn(new IdType(1L));

        // Act
        PractitionerDTO actualConvertPractitionerToUserDtoResult = practitionerConverter
                .convertPractitionerToUserDto(practitioner);

        // Assert
        verify(identifier).getValue();
        verify(practitioner).getActive();
        verify(practitioner).getGender();
        verify(practitioner).getIdentifierFirstRep();
        verify(practitioner, atLeast(1)).getNameFirstRep();
        verify(practitioner).getTelecom();
        verify(practitioner).getTelecomFirstRep();
        verify(practitioner).getIdElement();
        assertEquals("42", actualConvertPractitionerToUserDtoResult.getPhoneNumber());
        assertEquals("MALE", actualConvertPractitionerToUserDtoResult.getGender());
        assertEquals(1L, actualConvertPractitionerToUserDtoResult.getFhirId().longValue());
        assertEquals(42L, actualConvertPractitionerToUserDtoResult.getId().longValue());
        assertTrue(actualConvertPractitionerToUserDtoResult.isActive());
        assertTrue(actualConvertPractitionerToUserDtoResult.getRoles().isEmpty());

        StringType stringValue = new StringType("Your string value");
        practitioner.getNameFirstRep().getGiven().set(0, stringValue);
        practitioner.getNameFirstRep().setFamily("Sample");
        actualConvertPractitionerToUserDtoResult = practitionerConverter
                .convertPractitionerToUserDto(practitioner);

        // Assert
        assertEquals("42", actualConvertPractitionerToUserDtoResult.getPhoneNumber());
    }

    /**
     * Method under test:
     * {@link PractitionerConverter#convertUserRoleToFhirEntity(PractitionerRoleDTO)}
     */
    @Test
    void testConvertUserRoleToFhirEntity() {
        // Arrange
        PractitionerRoleDTO practitionerRoleDTO = new PractitionerRoleDTO();
        practitionerRoleDTO.setActive(true);
        practitionerRoleDTO.setAuthority("JaneDoe");
        practitionerRoleDTO
                .setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        practitionerRoleDTO.setCreatedBy(1L);
        practitionerRoleDTO.setDeleted(true);
        practitionerRoleDTO.setFhirRoleId(1L);
        practitionerRoleDTO.setId(1L);
        practitionerRoleDTO.setLevel("Level");
        practitionerRoleDTO.setName("Name");
        practitionerRoleDTO
                .setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        practitionerRoleDTO.setUpdatedBy(1L);

        // Act
        PractitionerRole actualConvertUserRoleToFhirEntityResult = practitionerConverter
                .convertUserRoleToFhirEntity(practitionerRoleDTO);

        // Assert
        Identifier identifierFirstRep = actualConvertUserRoleToFhirEntityResult.getIdentifierFirstRep();
        assertEquals("1", identifierFirstRep.getValue());
        assertEquals("http://mdtlabs.com/PractitionerRole", identifierFirstRep.getSystem());
        assertEquals(1, actualConvertUserRoleToFhirEntityResult.getCode().size());
        assertEquals(1, actualConvertUserRoleToFhirEntityResult.getIdentifier().size());
        assertTrue(actualConvertUserRoleToFhirEntityResult.getCodeFirstRep().hasCoding());
        assertTrue(identifierFirstRep.hasSystem());
        assertTrue(identifierFirstRep.hasValue());
        assertTrue(actualConvertUserRoleToFhirEntityResult.hasActive());
        BooleanType activeElement = actualConvertUserRoleToFhirEntityResult.getActiveElement();
        assertTrue(activeElement.getValue());
        String expectedValueAsString = Boolean.TRUE.toString();
        assertEquals(expectedValueAsString, activeElement.getValueAsString());
    }

    /**
     * Method under test:
     * {@link PractitionerConverter#convertPractitionerRoleToRoleDto(PractitionerRole)}
     */
    @Test
    void testConvertPractitionerRoleToRoleDto() {
        // Arrange
        Identifier identifier = mock(Identifier.class);
        when(identifier.getValue()).thenReturn("42");
        PractitionerRole practitionerRole = mock(PractitionerRole.class);
        when(practitionerRole.getActive()).thenReturn(true);
        when(practitionerRole.getIdentifierFirstRep()).thenReturn(identifier);
        when(practitionerRole.getIdElement()).thenReturn(new IdType(1L));

        // Act
        PractitionerRoleDTO actualConvertPractitionerRoleToRoleDtoResult = practitionerConverter
                .convertPractitionerRoleToRoleDto(practitionerRole);

        // Assert
        verify(identifier).getValue();
        verify(practitionerRole).getActive();
        verify(practitionerRole).getIdentifierFirstRep();
        verify(practitionerRole).getIdElement();
        assertEquals(1L, actualConvertPractitionerRoleToRoleDtoResult.getFhirRoleId());
        assertEquals(42L, actualConvertPractitionerRoleToRoleDtoResult.getId());
        assertTrue(actualConvertPractitionerRoleToRoleDtoResult.isActive());
    }
}

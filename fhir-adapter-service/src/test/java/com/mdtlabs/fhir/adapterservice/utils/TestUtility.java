package com.mdtlabs.fhir.adapterservice.utils;


import com.mdtlabs.fhir.commonservice.common.constants.FhirConstants;
import com.mdtlabs.fhir.commonservice.common.constants.TestConstants;
import com.mdtlabs.fhir.commonservice.common.model.dto.AssessmentResponseDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.BpLogDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.BpLogDetails;
import com.mdtlabs.fhir.commonservice.common.model.dto.EnrollmentResponseDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirAssessmentRequestDto;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirEnrollmentRequestDto;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirUserDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.GlucoseLogDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.MentalHealthDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.PatientDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.PatientMedicalCompliance;
import com.mdtlabs.fhir.commonservice.common.model.dto.PatientPregnancyDetails;
import com.mdtlabs.fhir.commonservice.common.model.dto.PatientSymptom;
import com.mdtlabs.fhir.commonservice.common.model.dto.PatientTracker;
import com.mdtlabs.fhir.commonservice.common.model.dto.PatientUpdateRequestDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.PractitionerDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.PractitionerRoleDTO;
import com.mdtlabs.fhir.commonservice.common.model.entity.SpiceFhirMapping;
import com.mdtlabs.fhir.commonservice.common.model.entity.UserSpiceFhirMapping;
import org.hl7.fhir.r4.model.Address;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.DateType;
import org.hl7.fhir.r4.model.Enumerations;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.QuestionnaireResponse;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.StringType;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class TestUtility {

    private static final Date MOCK_DATE = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    public static String getAssessmentBundleString() {
        return "{ \"resourceType\": \"Bundle\",\n" +
                "  \"type\": \"transaction\",\n" +
                "  \"entry\": [\n" +
                "    {\n" +
                "      \"fullUrl\": \"urn:uuid:assessment-123\",\n" +
                "      \"resource\": {\n" +
                "        \"resourceType\": \"QuestionnaireResponse\",\n" +
                "        \"id\": \"assessment-123\",\n" +
                "        \"status\": \"completed\",\n" +
                "        \"authored\": \"2024-03-08T12:00:00Z\",\n" +
                "        \"subject\": {\n" +
                "          \"reference\": \"Patient/example\"\n" +
                "        },\n" +
                "        \"questionnaire\": {\n" +
                "          \"reference\": \"Questionnaire/example\"\n" +
                "        },\n" +
                "        \"item\": [\n" +
                "          {\n" +
                "            \"linkId\": \"q1\",\n" +
                "            \"answer\": [\n" +
                "              {\n" +
                "                \"valueString\": \"Yes\"\n" +
                "              }\n" +
                "            ]\n" +
                "          },\n" +
                "          {\n" +
                "            \"linkId\": \"q2\",\n" +
                "            \"answer\": [\n" +
                "              {\n" +
                "                \"valueString\": \"No\"\n" +
                "              }\n" +
                "            ]\n" +
                "          },\n" +
                "          {\n" +
                "            \"linkId\": \"q3\",\n" +
                "            \"answer\": [\n" +
                "              {\n" +
                "                \"valueString\": \"Sometimes\"\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"request\": {\n" +
                "        \"method\": \"POST\",\n" +
                "        \"url\": \"QuestionnaireResponse\"\n" +
                "      },\n" +
                "      \"response\": {\n" +
                "        \"location\": \"Observation/123\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    public static String getIncorrectBundleString() {
        return "{ \"resourceType\": \"Bundle\",\n" +
                "  \"type\": \"transaction\",\n" +
                "  \"entry\": [\n" +
                "    {\n" +
                "      \"fullUrl\": \"urn:uuid:assessment-123\",\n" +
                "      \"resource\": {\n" +
                "        \"resourceType\": \"QuestionnaireResponse\",\n" +
                "        \"id\": \"assessment-123\",\n" +
                "        \"status\": \"completed\",\n" +
                "        \"authored\": \"2024-03-08T12:00:00Z\",\n" +
                "        \"subject\": {\n" +
                "          \"reference\": \"Patient/example\"\n" +
                "        },\n" +
                "        \"questionnaire\": {\n" +
                "          \"reference\": \"Questionnaire/example\"\n" +
                "        },\n" +
                "        \"item\": [\n" +
                "          {\n" +
                "            \"linkId\": \"q1\",\n" +
                "            \"answer\": [\n" +
                "              {\n" +
                "                \"valueString\": \"Yes\"\n" +
                "              }\n" +
                "            ]\n" +
                "          },\n" +
                "          {\n" +
                "            \"linkId\": \"q2\",\n" +
                "            \"answer\": [\n" +
                "              {\n" +
                "                \"valueString\": \"No\"\n" +
                "              }\n" +
                "            ]\n" +
                "          },\n" +
                "          {\n" +
                "            \"linkId\": \"q3\",\n" +
                "            \"answer\": [\n" +
                "              {\n" +
                "                \"valueString\": \"Sometimes\"\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"request\": {\n" +
                "        \"method\": \"POST\",\n" +
                "        \"url\": \"QuestionnaireResponse\"\n" +
                "      },\n" +
                "      \"response\": {\n" +
                "        \"location\": \"Incorrect/123\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    public static FhirAssessmentRequestDto getFhirAssessmentRequest() {
        FhirAssessmentRequestDto assessmentRequest = new FhirAssessmentRequestDto();
        assessmentRequest.setType("Routine Checkup");

        assessmentRequest.setBpLog(getBPLogDTO());

        GlucoseLogDTO glucoseLog = new GlucoseLogDTO();
        glucoseLog.setId(TestConstants.ONE_LONG);
        glucoseLog.setGlucoseType("Fasting");
        glucoseLog.setGlucoseValue(90.5);
        assessmentRequest.setGlucoseLog(glucoseLog);

        assessmentRequest.setPatientTrackId(1L);

        MentalHealthDTO mentalHealth = new MentalHealthDTO();
        assessmentRequest.setMentalHealth(mentalHealth);

        List<PatientSymptom> patientSymptomList = new ArrayList<>();
        PatientSymptom symptom1 = new PatientSymptom();
        symptom1.setName("Headache");
        patientSymptomList.add(symptom1);

        PatientSymptom symptom2 = new PatientSymptom();
        symptom2.setName("Fatigue");
        patientSymptomList.add(symptom2);

        assessmentRequest.setPatientSymptomList(patientSymptomList);

        PatientPregnancyDetails pregnancyDetails = new PatientPregnancyDetails();
        assessmentRequest.setPatientPregnancyDetails(pregnancyDetails);

        List<PatientMedicalCompliance> patientMedicalComplianceList = new ArrayList<>();
        PatientMedicalCompliance compliance1 = new PatientMedicalCompliance();
        patientMedicalComplianceList.add(compliance1);

        PatientMedicalCompliance compliance2 = new PatientMedicalCompliance();
        patientMedicalComplianceList.add(compliance2);

        assessmentRequest.setPatientMedicalComplianceList(patientMedicalComplianceList);

        PatientTracker patientTracker = new PatientTracker();
        assessmentRequest.setPatientTracker(patientTracker);

        assessmentRequest.setCreatedBy(1L);
        assessmentRequest.setUpdatedBy(1L);

        return assessmentRequest;
    }

    public static BpLogDTO getBPLogDTO() {
        BpLogDTO bpLog = new BpLogDTO();
        bpLog.setId(1L);
        bpLog.setAvgSystolic(120);
        bpLog.setAvgDiastolic(80);
        bpLog.setAvgPulse(70);

        List<BpLogDetails> bpLogDetailsList = new ArrayList<>();
        BpLogDetails bpLogDetail1 = new BpLogDetails();
        bpLogDetail1.setSystolic(122);
        bpLogDetail1.setDiastolic(78);
        bpLogDetail1.setPulse(72);
        bpLogDetailsList.add(bpLogDetail1);

        BpLogDetails bpLogDetail2 = new BpLogDetails();
        bpLogDetail2.setSystolic(124);
        bpLogDetail2.setDiastolic(76);
        bpLogDetail2.setPulse(74);
        bpLogDetailsList.add(bpLogDetail2);

        bpLog.setBpLogDetails(bpLogDetailsList);

        return bpLog;
    }

    public static Observation getBPObservation() {
        Observation observation = new Observation();

        CodeableConcept codeableConcept = new CodeableConcept();

        String desiredCodeText = FhirConstants.BLOOD_PRESSURE;
        Coding coding = new Coding();
        coding.setCode(desiredCodeText);

        codeableConcept.addCoding(coding);
        observation.setCode(codeableConcept);

        observation.getCode().setText(FhirConstants.BLOOD_PRESSURE);

        return observation;
    }

    public static Observation getBGObservation() {
        Observation observation = new Observation();

        CodeableConcept codeableConcept = new CodeableConcept();

        String desiredCodeText = FhirConstants.BLOOD_GLUCOSE;
        Coding coding = new Coding();
        coding.setCode(desiredCodeText);

        codeableConcept.addCoding(coding);
        observation.setCode(codeableConcept);

        observation.getCode().setText(FhirConstants.BLOOD_GLUCOSE);

        return observation;
    }

    public static Observation createSampleIncorrectObservation() {
        Observation observation = new Observation();

        CodeableConcept codeableConcept = new CodeableConcept();

        Coding coding = new Coding();

        codeableConcept.addCoding(coding);
        observation.setCode(codeableConcept);

        return observation;
    }

    public static FhirUserDTO createMockFhirUserRequestDto() {
        FhirUserDTO userDTO = new FhirUserDTO();
        userDTO.setAccountExpired(true);
        userDTO.setAccountLocked(true);
        userDTO.setAddress("42 Main St");
        userDTO.setAuthorization("JaneDoe");
        userDTO.setBlockedDate(MOCK_DATE);
        userDTO.setCatchmentArea("Catchment Area");
        userDTO.setCommunityUnion("Community Union");
        userDTO.setCountryCode("GB");
        userDTO.setCreatedAt(MOCK_DATE);
        userDTO.setCreatedBy(1L);
        userDTO.setCredentialsExpired(true);
        userDTO.setCultureId(1L);
        userDTO.setCurrentDate(1L);
        userDTO.setDeleted(true);
        userDTO.setDeviceInfoId(1L);
        userDTO.setFirstName("Jane");
        userDTO.setForgetPasswordCount(3);
        userDTO.setForgetPasswordTime(MOCK_DATE);
        userDTO.setForgetPasswordToken("ABC123");
        userDTO.setGender("male");
        userDTO.setId(1L);
        userDTO.setInvalidLoginAttempts(1);
        userDTO.setInvalidLoginTime(MOCK_DATE);
        userDTO.setInvalidResetTime(MOCK_DATE);
        userDTO.setIsBlocked(true);
        userDTO.setIsPasswordResetEnabled(true);
        userDTO.setIsSuperUser(true);
        userDTO.setLastLoggedIn(MOCK_DATE);
        userDTO.setLastLoggedOut(MOCK_DATE);
        userDTO.setLastName("Doe");
        userDTO.setLicenseAcceptance(true);
        userDTO.setMiddleName("Middle Name");
        userDTO.setPassword("test");
        userDTO.setPasswordResetAttempts(1);
        userDTO.setPhoneNumber("6625550144");
        userDTO.setRoles(new ArrayList<>());
        userDTO.setSiteId(1L);
        userDTO.setSiteName("Site Name");
        userDTO.setSubject("Hello from the Dreaming Spires");
        userDTO.setTenantId(1L);
        userDTO.setUpdatedAt(MOCK_DATE);
        userDTO.setUpdatedBy(1L);
        userDTO.setUsername("Jane");
        return userDTO;
    }

    public static PractitionerDTO createMockPractitionerDTO() {
        PractitionerDTO practitionerDTO = new PractitionerDTO();
        practitionerDTO.setActive(true);
        practitionerDTO.setAddress("42 Main St");
        practitionerDTO.setBlockedDate(MOCK_DATE);
        practitionerDTO.setCountryCode("GB");
        practitionerDTO.setCountryId(1L);
        practitionerDTO.setCreatedAt(MOCK_DATE);
        practitionerDTO.setCreatedBy(1L);
        practitionerDTO.setCultureId(1L);
        practitionerDTO.setDeleted(true);
        practitionerDTO.setFhirId(1L);
        practitionerDTO.setFirstName("Jane");
        practitionerDTO.setForgetPasswordCount(3);
        practitionerDTO.setForgetPasswordTime(MOCK_DATE);
        practitionerDTO.setForgetPasswordToken("ABC123");
        practitionerDTO.setGender("Gender");
        practitionerDTO.setId(1L);
        practitionerDTO.setInvalidLoginAttempts(1);
        practitionerDTO.setInvalidLoginTime(MOCK_DATE);
        practitionerDTO.setInvalidResetTime(MOCK_DATE);
        practitionerDTO.setIsBlocked(true);
        practitionerDTO.setIsPasswordResetEnabled(true);
        practitionerDTO.setLastLoggedIn(MOCK_DATE);
        practitionerDTO.setLastLoggedOut(MOCK_DATE);
        practitionerDTO.setLastName("Doe");
        practitionerDTO.setLicenseAcceptance(true);
        practitionerDTO.setMiddleName("Middle Name");
        practitionerDTO.setPassword("test");
        practitionerDTO.setPasswordResetAttempts(1);
        practitionerDTO.setPhoneNumber("6625550144");
        practitionerDTO.setRoles(new HashSet<>());
        practitionerDTO.setTenantId(1L);
        practitionerDTO.setTimezoneId(1L);
        practitionerDTO.setUpdatedAt(MOCK_DATE);
        practitionerDTO.setUpdatedBy(1L);
        practitionerDTO.setUsername("Jane");
        return practitionerDTO;
    }

    public static UserSpiceFhirMapping createMockUserSpiceFhirMapping() {
        UserSpiceFhirMapping userSpiceFhirMapping = new UserSpiceFhirMapping();
        userSpiceFhirMapping.setActive(true);
        userSpiceFhirMapping.setCreatedAt(MOCK_DATE);
        userSpiceFhirMapping.setDeleted(true);
        userSpiceFhirMapping.setFhirPractitionerId(1L);
        userSpiceFhirMapping.setId(1L);
        userSpiceFhirMapping.setSpiceUserId(1L);
        userSpiceFhirMapping.setUpdatedAt(MOCK_DATE);
        return userSpiceFhirMapping;
    }

    public static PractitionerRoleDTO createMockPractitionerRoleDTO() {
        PractitionerRoleDTO roleDetails = new PractitionerRoleDTO();
        roleDetails.setActive(true);
        roleDetails.setAuthority("JaneDoe");
        roleDetails.setCreatedAt(MOCK_DATE);
        roleDetails.setCreatedBy(1L);
        roleDetails.setDeleted(true);
        roleDetails.setFhirRoleId(1L);
        roleDetails.setId(1L);
        roleDetails.setLevel("Level");
        roleDetails.setName("Name");
        roleDetails.setUpdatedAt(MOCK_DATE);
        roleDetails.setUpdatedBy(1L);
        return roleDetails;
    }

    public static AssessmentResponseDTO createAssessmentResponseDTO() {
        AssessmentResponseDTO assessmentResponseDTO = new AssessmentResponseDTO();
        assessmentResponseDTO.setBpLog("Bp Log");
        assessmentResponseDTO
                .setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        assessmentResponseDTO.setCreatedBy(1L);
        assessmentResponseDTO.setGlucoseLog("Glucose Log");
        assessmentResponseDTO.setPatientTrackId(1L);
        assessmentResponseDTO
                .setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        assessmentResponseDTO.setUpdatedBy(1L);
        return assessmentResponseDTO;
    }

    public static FhirEnrollmentRequestDto getFHIREnrollmentRequest() {
        FhirEnrollmentRequestDto enrollmentRequestDTO = new FhirEnrollmentRequestDto();
        enrollmentRequestDTO.setType("Some type");
        enrollmentRequestDTO.setPatientTrackId(1L);
        enrollmentRequestDTO.setPatient(getPatient());
        enrollmentRequestDTO.setVillage("Some village");
        enrollmentRequestDTO.setCreatedBy(1L);
        enrollmentRequestDTO.setUpdatedBy(1L);
        enrollmentRequestDTO.setCreatedAt(new Date());
        enrollmentRequestDTO.setUpdatedAt(new Date());
        enrollmentRequestDTO.setBpLog(getBPLogDTO());

        return enrollmentRequestDTO;
    }

    public static PatientDTO getPatient() {
        PatientDTO patient = new PatientDTO();
        patient.setId(1L);
        patient.setAge(30);
        patient.setGender("Male");
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setIdentityValue("1234567890");
        patient.setPhoneNumber("123-456-7890");
        patient.setPhoneNumberCategory("Home");
        patient.setLandmark("Nearby landmark");
        patient.setOccupation("Software Engineer");
        patient.setLevelOfEducation("Bachelor's");
        patient.setCreatedAt(new Date());
        patient.setUpdatedAt(new Date());
        return patient;
    }

    public static String getPractitionerBundleString() {
        return "{\n" +
                "  \"resourceType\": \"Bundle\",\n" +
                "  \"type\": \"transaction\",\n" +
                "  \"entry\": [\n" +
                "    {\n" +
                "      \"fullUrl\": \"urn:uuid:practitioner-123\",\n" +
                "      \"resource\": {\n" +
                "        \"resourceType\": \"Practitioner\",\n" +
                "        \"id\": \"practitioner-123\",\n" +
                "        \"active\": true,\n" +
                "        \"name\": [\n" +
                "          {\n" +
                "            \"family\": \"Doe\",\n" +
                "            \"given\": [\n" +
                "              \"John\"\n" +
                "            ],\n" +
                "            \"prefix\": [\n" +
                "              \"Dr\"\n" +
                "            ]\n" +
                "          }\n" +
                "        ],\n" +
                "        \"telecom\": [\n" +
                "          {\n" +
                "            \"system\": \"phone\",\n" +
                "            \"value\": \"123-456-7890\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"address\": [\n" +
                "          {\n" +
                "            \"use\": \"home\",\n" +
                "            \"line\": [\n" +
                "              \"123 Main St\",\n" +
                "              \"Apt 101\"\n" +
                "            ],\n" +
                "            \"city\": \"Any-town\",\n" +
                "            \"state\": \"NY\",\n" +
                "            \"postalCode\": \"12345\",\n" +
                "            \"country\": \"USA\"\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"request\": {\n" +
                "        \"method\": \"POST\",\n" +
                "        \"url\": \"Practitioner\"\n" +
                "      },\n" +
                "      \"response\": {\n" +
                "        \"location\": \"Practitioner/123\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    public static String getEnrollmentBundleString() {
        return "{\n" +
                "  \"resourceType\": \"Bundle\",\n" +
                "  \"type\": \"transaction\",\n" +
                "  \"entry\": [\n" +
                "    {\n" +
                "      \"fullUrl\": \"urn:uuid:enrollment-123\",\n" +
                "      \"resource\": {\n" +
                "        \"resourceType\": \"Enrollment\",\n" +
                "        \"id\": \"enrollment-123\",\n" +
                "        \"status\": \"active\",\n" +
                "        \"subject\": {\n" +
                "          \"reference\": \"Patient/example\"\n" +
                "        },\n" +
                "        \"insurer\": {\n" +
                "          \"reference\": \"Organization/insurer-example\"\n" +
                "        },\n" +
                "        \"coverage\": {\n" +
                "          \"reference\": \"Coverage/example\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"request\": {\n" +
                "        \"method\": \"POST\",\n" +
                "        \"url\": \"Enrollment\"\n" +
                "      },\n" +
                "      \"response\": {\n" +
                "        \"location\": \"Enrollment/123\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    public static Bundle getAssessmentBundle() {
        Bundle bundle = new Bundle();
        bundle.setType(Bundle.BundleType.TRANSACTION);

        QuestionnaireResponse questionnaireResponse = new QuestionnaireResponse();
        questionnaireResponse.setId("assessment-123");
        questionnaireResponse.setStatus(QuestionnaireResponse.QuestionnaireResponseStatus.COMPLETED);
        questionnaireResponse.setAuthored(new Date());

        questionnaireResponse.setSubject(new Reference("Patient/example"));

        QuestionnaireResponse.QuestionnaireResponseItemComponent item1 = new QuestionnaireResponse.QuestionnaireResponseItemComponent();
        item1.setLinkId("q1");
        item1.addAnswer().setValue(new StringType("Yes"));
        questionnaireResponse.addItem(item1);

        QuestionnaireResponse.QuestionnaireResponseItemComponent item2 = new QuestionnaireResponse.QuestionnaireResponseItemComponent();
        item2.setLinkId("q2");
        item2.addAnswer().setValue(new StringType("No"));
        questionnaireResponse.addItem(item2);

        QuestionnaireResponse.QuestionnaireResponseItemComponent item3 = new QuestionnaireResponse.QuestionnaireResponseItemComponent();
        item3.setLinkId("q3");
        item3.addAnswer().setValue(new StringType("Sometimes"));
        questionnaireResponse.addItem(item3);

        Bundle.BundleEntryComponent entry = new Bundle.BundleEntryComponent();
        entry.setFullUrl("urn:uuid:assessment-123");
        entry.setResource(questionnaireResponse);
        entry.setRequest(new Bundle.BundleEntryRequestComponent().setMethod(Bundle.HTTPVerb.POST).setUrl("QuestionnaireResponse"));

        bundle.addEntry(entry);

        return bundle;
    }

    public static Patient getFHIPatient() {
        Patient patient = new Patient();
        patient.setId("example");
        patient.setActive(true);


        Identifier identifier = new Identifier();
        identifier.setSystem("http://example.org/fhir/Patient");
        identifier.setValue("12345");
        patient.addIdentifier(identifier);


        HumanName name = new HumanName();
        name.setFamily("Doe");
        name.addGiven("John");
        patient.addName(name);


        patient.setGender(Enumerations.AdministrativeGender.MALE);


        patient.setBirthDateElement(new DateType("1980-01-01"));


        Address address = new Address();
        address.addLine("123 Main St");
        address.setCity("Any-town");
        address.setPostalCode("12345");
        patient.addAddress(address);

        return patient;
    }

    public static Bundle createEnrollmentFhirBundle() {

        FhirEnrollmentRequestDto enrollmentRequestDTO = getFHIREnrollmentRequest();

        Patient patient = getFHIPatient();


        Observation bpLogObservation = getBPObservation();
        bpLogObservation.setId("1L");


        Bundle bundle = new Bundle().setType(Bundle.BundleType.TRANSACTION);


        bundle.addEntry()
                .setFullUrl(FhirConstants.PATIENT_FULL_URL)
                .setResource(patient)
                .getRequest()
                .setMethod(Bundle.HTTPVerb.POST)
                .setUrl(FhirConstants.PATIENT);


        bundle.addEntry()
                .setResource(bpLogObservation)
                .getRequest()
                .setMethod(Bundle.HTTPVerb.POST)
                .setUrl(FhirConstants.OBSERVATION);


        if (enrollmentRequestDTO.getGlucoseLog() != null) {
            Observation glucoseLogObservation = getBGObservation();
            glucoseLogObservation.setId("1L");
            bundle.addEntry()
                    .setResource(glucoseLogObservation)
                    .getRequest()
                    .setMethod(Bundle.HTTPVerb.POST)
                    .setUrl(FhirConstants.OBSERVATION);
        }

        return bundle;
    }

    public static EnrollmentResponseDTO getEnrollmentResponseDTO() {
        EnrollmentResponseDTO enrollmentResponseDTO = new EnrollmentResponseDTO();
        enrollmentResponseDTO.setGlucoseLog("Sample Glucose Log");
        enrollmentResponseDTO.setBpLog("Sample BP Log");
        enrollmentResponseDTO.setPatient("Sample Patient");
        enrollmentResponseDTO.setCreatedBy(123456L);
        enrollmentResponseDTO.setUpdatedBy(789012L);
        enrollmentResponseDTO.setCreatedAt(new Date());
        enrollmentResponseDTO.setUpdatedAt(new Date());

        return enrollmentResponseDTO;

    }

    public static SpiceFhirMapping createTestSpiceFhirMapping() {
        SpiceFhirMapping spiceFhirMapping = new SpiceFhirMapping();
        spiceFhirMapping.setFhirId(1);
        spiceFhirMapping.setFhirResourceType("Fhir Resource Type");
        spiceFhirMapping.setId(1L);
        spiceFhirMapping.setReason("Just cause");
        spiceFhirMapping.setSpiceId(1);
        spiceFhirMapping.setSpiceModule("Spice Module");
        spiceFhirMapping.setSpiceModuleId(1L);
        spiceFhirMapping.setStatus("Status");
        return spiceFhirMapping;
    }

    public static PatientTracker createTestPatientTracker() {
        PatientTracker patientTracker = new PatientTracker();
        patientTracker.setAccountId(1L);
        patientTracker.setActive(true);
        patientTracker.setAge(1);
        patientTracker.setAvgDiastolic(1);
        patientTracker.setAvgPulse(1);
        patientTracker.setAvgSystolic(1);
        patientTracker.setBmi(10.0d);
        patientTracker.setCageAid(1);
        patientTracker.setConfirmDiagnosis(new ArrayList<>());
        patientTracker.setCountryId(1L);
        patientTracker.setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        patientTracker.setCreatedBy(1L);
        patientTracker.setCvdRiskLevel("Cvd Risk Level");
        patientTracker.setCvdRiskScore(3);
        patientTracker.setDateOfBirth(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        patientTracker.setDeleteOtherReason("Just cause");
        patientTracker.setDeleteReason("Just cause");
        patientTracker.setDeleted(true);
        patientTracker.setDiagnosisComments("Diagnosis Comments");
        patientTracker.setEnrollmentAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        patientTracker.setFirstName("Jane");
        patientTracker.setGad7RiskLevel("Gad7 Risk Level");
        patientTracker.setGad7Score(3);
        patientTracker.setGender("Gender");
        patientTracker.setGlucoseType("Glucose Type");
        patientTracker.setGlucoseUnit("Glucose Unit");
        patientTracker.setGlucoseValue(10.0d);
        patientTracker.setHeight(10.0d);
        patientTracker.setId(1L);
        patientTracker.setIdentityType("Identity Type");
        patientTracker.setIdentityValue("42");
        patientTracker.setInitialReview(true);
        patientTracker.setIsConfirmDiagnosis(true);
        patientTracker.setIsDiabetesDiagnosis(true);
        patientTracker.setIsHtnDiagnosis(true);
        patientTracker.setIsObservation(true);
        patientTracker.setIsPregnancyRisk(true);
        patientTracker.setIsPregnant(true);
        patientTracker.setIsRegularSmoker(true);
        patientTracker.setIsScreening(true);
        patientTracker.setLabTestReferred(true);
        patientTracker.setLastAssessmentDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        patientTracker.setLastLabtestReferredDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        patientTracker.setLastMedicationPrescribedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        patientTracker.setLastName("Doe");
        patientTracker.setLastReviewDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        patientTracker.setMedicationPrescribed(true);
        patientTracker.setNextBgAssessmentDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        patientTracker.setNextBpAssessmentDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        patientTracker.setNextMedicalReviewDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        patientTracker.setOperatingUnitId(1L);
        patientTracker.setPatientId(1L);
        patientTracker.setPatientStatus("Patient Status");
        patientTracker.setPhoneNumber("6625550144");
        patientTracker.setPhq4FirstScore(3);
        patientTracker.setPhq4RiskLevel("Phq4 Risk Level");
        patientTracker.setPhq4Score(3);
        patientTracker.setPhq4SecondScore(3);
        patientTracker.setPhq9RiskLevel("Phq9 Risk Level");
        patientTracker.setPhq9Score(3);
        patientTracker.setProgramId(1L);
        patientTracker.setProvisionalDiagnosis(new ArrayList<>());
        patientTracker.setPsychologicalAssessment(true);
        patientTracker.setQrCode("Qr Code");
        patientTracker.setRedRiskPatient(true);
        patientTracker.setRiskLevel("Risk Level");
        patientTracker.setScreeningLogId(1L);
        patientTracker.setScreeningReferral(true);
        patientTracker.setSiteId(1L);
        patientTracker.setSuicidalIdeation("Suicidal Ideation");
        patientTracker.setTenantId(1L);
        patientTracker.setTotalCount(3L);
        patientTracker.setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        patientTracker.setUpdatedBy(1L);
        patientTracker.setVillage("Village");
        patientTracker.setWeight(10.0d);
        return patientTracker;
    }

    public static PatientUpdateRequestDTO getPatientUpdateRequest() {
        PatientUpdateRequestDTO patientUpdateRequestDTO = new PatientUpdateRequestDTO();
        patientUpdateRequestDTO.setCreatedBy(1L);
        patientUpdateRequestDTO.setPatientDTO(new PatientDTO());
        patientUpdateRequestDTO.setPatientTracker(createTestPatientTracker());
        patientUpdateRequestDTO.setType("Type");
        patientUpdateRequestDTO.setUpdatedBy(1L);
        patientUpdateRequestDTO.setPatientDTO(getPatientObject());
        return patientUpdateRequestDTO;
    }

    public static PatientDTO getPatientObject() {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(1L); // Example value, replace with actual value
        patientDTO.setGender("Male"); // Example value, replace with actual value
        patientDTO.setAge(30); // Example value, replace with actual value
        patientDTO.setFirstName("John"); // Example value, replace with actual value
        patientDTO.setLastName("Doe"); // Example value, replace with actual value
        patientDTO.setIdentityValue("1234567890"); // Example value, replace with actual value
        patientDTO.setPhoneNumber("6625550144"); // Example value, replace with actual value
        patientDTO.setPhoneNumberCategory("Mobile"); // Example value, replace with actual value
        patientDTO.setCountryId(1L); // Example value, replace with actual value
        patientDTO.setCountyId(1L); // Example value, replace with actual value
        patientDTO.setSubCountyId(1L); // Example value, replace with actual value
        patientDTO.setSiteId(1L); // Example value, replace with actual value
        patientDTO.setCreatedAt(new Date()); // Example value, replace with actual value
        patientDTO.setUpdatedAt(new Date()); // Example value, replace with actual value
        patientDTO.setCreatedBy(1L); // Example value, replace with actual value
        patientDTO.setUpdatedBy(1L); // Example value, replace with actual value
        patientDTO.setLandmark("Landmark"); // Example value
        patientDTO.setOccupation("occupation"); //
        patientDTO.setLevelOfEducation("levelOfEducation"); //
        return patientDTO;

    }

    public static SpiceFhirMapping getFhirMapping() {
        SpiceFhirMapping spiceFhirMapping = new SpiceFhirMapping();
        spiceFhirMapping.setFhirId(1);
        spiceFhirMapping.setFhirResourceType("Fhir Resource Type");
        spiceFhirMapping.setId(1L);
        spiceFhirMapping.setReason("Just cause");
        spiceFhirMapping.setSpiceId(1);
        spiceFhirMapping.setSpiceModule("Spice Module");
        spiceFhirMapping.setSpiceModuleId(1L);
        spiceFhirMapping.setStatus("Status");
        return spiceFhirMapping;
    }

    public static String getSpiceEnrollmentJsonMessageString() {
        return "{\"type\":\"Enrollment_Data\",\"patientTrackId\":521371,\"patient\":{\"id\":194635,\"createdBy\":296,\"updatedBy\":296,\"createdAt\":\"2023-12-27T06:30:31+00:00\",\"updatedAt\":\"2023-12-27T06:30:31+00:00\",\"tenantId\":107,\"nationalId\":\"TEST@17\",\"firstName\":\"TEST\",\"middleName\":\"TEST\",\"lastName\":\"T\",\"gender\":\"Female\",\"dateOfBirth\":-354173400000,\"age\":65,\"isPregnant\":true,\"phoneNumber\":\"6475786854\",\"phoneNumberCategory\":\"Personal\",\"countryId\":4,\"countyId\":7,\"subCountyId\":480,\"siteId\":4,\"landmark\":\"test\",\"occupation\":\"Self Employed\",\"levelOfEducation\":\"College/University Completed\",\"insuranceStatus\":false,\"insuranceType\":null,\"insuranceId\":null,\"otherInsurance\":null,\"isSupportGroup\":null,\"supportGroup\":null,\"isRegularSmoker\":false,\"programId\":12,\"initial\":\"E\",\"otherIdType\":null,\"languages\":null,\"ethnicity\":null,\"idType\":null,\"otherLanguages\":null,\"erVisitReason\":null,\"lote\":null,\"homeMedicalDevices\":null,\"erVisitFrequency\":null,\"emrNumber\":null,\"isErVisitHistory\":null,\"zipCode\":null,\"virtualId\":1088607,\"qrCode\":null,\"active\":true,\"deleted\":false},\"village\":\"test\",\"bpLog\":{\"id\":1341463,\"createdBy\":296,\"updatedBy\":296,\"createdAt\":\"2023-12-27T06:30:32+00:00\",\"updatedAt\":\"2023-12-27T06:30:32+00:00\",\"tenantId\":107,\"avgSystolic\":88,\"avgDiastolic\":88,\"height\":182.0,\"weight\":55.0,\"bmi\":16.6,\"temperature\":23.0,\"cvdRiskLevel\":\"Low risk\",\"cvdRiskScore\":6,\"isRegularSmoker\":false,\"type\":\"enrollment\",\"patientTrackId\":521371,\"bpLogDetails\":[{\"systolic\":88,\"diastolic\":88},{\"systolic\":88,\"diastolic\":88},{\"systolic\":88,\"diastolic\":88}],\"bpTakenOn\":1703658632578,\"assessmentTenantId\":48,\"uuid\":\"521371-88-88-enrollment-2023.12.27.06\",\"updatedFromEnrollment\":false,\"oldRecord\":false,\"latest\":true,\"redRiskPatient\":false,\"active\":true,\"deleted\":false},\"glucoseLog\":{\"id\":643447,\"createdBy\":296,\"updatedBy\":296,\"createdAt\":\"2023-12-27T06:30:32+00:00\",\"updatedAt\":\"2023-12-27T06:30:32+00:00\",\"tenantId\":107,\"hba1c\":22.0,\"glucoseType\":\"rbs\",\"glucoseValue\":22.0,\"lastMealTime\":1697083200000,\"glucoseDateTime\":1697104677000,\"hba1cDateTime\":1697104677000,\"glucoseUnit\":\"mmol/L\",\"hba1cUnit\":\"mmol/L\",\"type\":\"enrollment\",\"patientTrackId\":521371,\"screeningId\":null,\"assessmentTenantId\":48,\"bgTakenOn\":1703658632474,\"isBeforeDiabetesDiagnosis\":null,\"glucoseLogId\":null,\"diabetes\":null,\"diabetesOtherSymptoms\":null,\"uuid\":\"521371-rbs-22.0-enrollment-2023.12.27.06\",\"updatedFromEnrollment\":false,\"oldRecord\":false,\"latest\":true,\"active\":true,\"deleted\":false},\"createdBy\":12,\"updatedBy\":12,\"createdAt\":1703658631925,\"updatedAt\":1703658631925}";
    }

    public static String getSpiceAssessmentJsonMessageString() {
        return "{\"type\":\"Assessment_Data\"," +
                "\"bpLog\":{\"id\":1341456,\"createdBy\":296,\"updatedBy\":296,\"createdAt\":\"2023-12-26T07:52:37+00:00\",\"updatedAt\":\"2023-12-26T07:52:37+00:00\",\"tenantId\":96,\"avgSystolic\":88,\"avgDiastolic\":88,\"height\":182.0,\"weight\":55.0,\"bmi\":16.6,\"temperature\":33.0,\"cvdRiskLevel\":\"Low risk\",\"cvdRiskScore\":2,\"isRegularSmoker\":true,\"type\":\"assessment\",\"patientTrackId\":520494,\"bpLogDetails\":[{\"systolic\":88,\"diastolic\":88},{\"systolic\":88,\"diastolic\":88},{\"systolic\":88,\"diastolic\":88}],\"riskLevel\":\"High\",\"assessmentCategory\":\"Facility\",\"bpTakenOn\":1703577157959,\"assessmentTenantId\":48,\"unitMeasurement\":\"metric\",\"uuid\":\"520494-88-88-assessment-2023.12.26.07\",\"latest\":true,\"redRiskPatient\":false,\"updatedFromEnrollment\":false,\"oldRecord\":false,\"active\":true,\"deleted\":false}," +
                "\"glucoseLog\":{\"id\":643440,\"createdBy\":296,\"updatedBy\":296,\"createdAt\":\"2023-12-26T07:52:38+00:00\",\"updatedAt\":\"2023-12-26T07:52:38+00:00\",\"tenantId\":96,\"hba1c\":22.0,\"glucoseType\":\"fbs\",\"glucoseValue\":22.0,\"lastMealTime\":1700409600000,\"glucoseDateTime\":1700471979000,\"hba1cDateTime\":1700471979000,\"glucoseUnit\":\"mmol/L\",\"hba1cUnit\":\"%\",\"type\":\"assessment\",\"patientTrackId\":520494,\"screeningId\":null,\"assessmentTenantId\":48,\"bgTakenOn\":1703577158027,\"isBeforeDiabetesDiagnosis\":null,\"glucoseLogId\":null,\"diabetes\":null,\"diabetesOtherSymptoms\":null,\"uuid\":\"520494-fbs-22.0-assessment-2023.12.26.07\",\"latest\":true,\"updatedFromEnrollment\":false,\"oldRecord\":false,\"active\":true,\"deleted\":false},\"mentalHealth\":null,\"patientSymptomList\":[{\"id\":781000,\"createdBy\":296,\"updatedBy\":296,\"createdAt\":\"2023-12-26T07:52:39+00:00\",\"updatedAt\":\"2023-12-26T07:52:39+00:00\",\"tenantId\":96,\"name\":\"Swelling of tongue or lips\",\"type\":\"Hypertension\",\"patientTrackId\":520494,\"symptomId\":35,\"otherSymptom\":null,\"bpLogId\":1341456,\"glucoseLogId\":null,\"assessmentLogId\":119039,\"patientPregnancyId\":null,\"assessmentTenantId\":48,\"active\":true,\"deleted\":false}],\"patientPregnancyDetails\":{\"id\":351,\"createdBy\":8176,\"updatedBy\":8176,\"createdAt\":\"2023-07-31T10:07:00+00:00\",\"updatedAt\":\"2023-07-31T10:07:43+00:00\",\"tenantId\":96,\"pregnancyFetusesNumber\":null,\"gravida\":null,\"parity\":null,\"temperature\":null,\"lastMenstrualPeriodDate\":1667865600000,\"estimatedDeliveryDate\":1692057600000,\"isOnTreatment\":null,\"diagnosisTime\":null,\"diagnosis\":null,\"neonatalOutcomes\":null,\"maternalOutcomes\":null,\"attendedAncClinic\":true,\"actualDeliveryDate\":null,\"isInterestedToEnroll\":null,\"isIptDrugProvided\":null,\"isIronFolateProvided\":null,\"isMosquitoNetProvided\":null,\"isLatest\":true,\"isInitialReview\":true,\"isDangerSymptom\":true,\"patientTrackId\":520494,\"confirmDiagnosis\":null,\"active\":true,\"deleted\":false},\"redRiskNotification\":{\"id\":63304,\"createdBy\":296,\"updatedBy\":296,\"createdAt\":\"2023-12-26T07:52:38+00:00\",\"updatedAt\":\"2023-12-26T07:52:38+00:00\",\"tenantId\":96,\"bpLogId\":1341456,\"glucoseLogId\":643440,\"patientTrackId\":520494,\"status\":\"NEW\",\"assessmentLogId\":119039,\"active\":true,\"deleted\":false},\"patientMedicalComplianceList\":[{\"id\":593444,\"createdBy\":296,\"updatedBy\":296,\"createdAt\":\"2023-12-26T07:52:39+00:00\",\"updatedAt\":\"2023-12-26T07:52:39+00:00\",\"tenantId\":96,\"name\":\"Took all medication\",\"otherCompliance\":null,\"patientTrackId\":520494,\"complianceId\":6,\"bpLogId\":1341456,\"assessmentLogId\":119039,\"assessmentTenantId\":48,\"active\":true,\"deleted\":false}],\"patientAssessment\":{\"id\":119039,\"createdBy\":296,\"updatedBy\":296,\"createdAt\":\"2023-12-26T07:52:38+00:00\",\"updatedAt\":\"2023-12-26T07:52:38+00:00\",\"tenantId\":96,\"bpLogId\":1341456,\"glucoseLogId\":643440,\"type\":\"assessment\",\"patientTrackId\":520494,\"assessmentTenantId\":48,\"mentalHealthId\":1424,\"active\":true,\"deleted\":false},\"patientTracker\":{\"id\":520494,\"createdBy\":8176,\"updatedBy\":296,\"createdAt\":\"2023-07-31T10:07:00+00:00\",\"updatedAt\":\"2023-12-26T07:31:38+00:00\",\"tenantId\":96,\"nationalId\":\"ARUKA123\",\"firstName\":\"ARUKA\",\"lastName\":\"S\",\"dateOfBirth\":null,\"age\":33,\"gender\":\"Female\",\"phoneNumber\":\"298349575\",\"height\":182.0,\"weight\":55.0,\"bmi\":16.6,\"isRegularSmoker\":true,\"countryId\":1,\"siteId\":159,\"operatingUnitId\":19,\"accountId\":2,\"programId\":null,\"avgSystolic\":88,\"avgDiastolic\":88,\"avgPulse\":null,\"glucoseUnit\":\"mmol/L\",\"glucoseType\":\"fbs\",\"glucoseValue\":22.0,\"cvdRiskLevel\":\"Low risk\",\"cvdRiskScore\":2,\"screeningLogId\":472434,\"nextMedicalReviewDate\":null,\"nextBpAssessmentDate\":null,\"nextBgAssessmentDate\":null,\"patientId\":null,\"patientStatus\":\"NONE\",\"isObservation\":null,\"isScreening\":true,\"screeningReferral\":true,\"enrollmentAt\":null,\"lastReviewDate\":1690798295950,\"lastAssessmentDate\":1703577159800,\"isConfirmDiagnosis\":null,\"diagnosisComments\":null,\"confirmDiagnosis\":null,\"provisionalDiagnosis\":[\"DM\"],\"phq4Score\":7,\"phq4RiskLevel\":\"Moderate\",\"phq4FirstScore\":3,\"phq4SecondScore\":4,\"phq9Score\":12,\"phq9RiskLevel\":\"Severe\",\"gad7Score\":11,\"gad7RiskLevel\":\"Severe\",\"riskLevel\":\"High\",\"isPregnant\":true,\"lastMedicationPrescribedDate\":null,\"isHtnDiagnosis\":null,\"isDiabetesDiagnosis\":null,\"lastLabtestReferredDate\":null,\"totalCount\":0,\"deleteReason\":null,\"deleteOtherReason\":null,\"qrCode\":null,\"village\":null,\"suicidalIdeation\":\"Yes\",\"cageAid\":2,\"isPregnancyRisk\":true,\"psychologicalAssessment\":true,\"medicationPrescribed\":false,\"redRiskPatient\":true,\"labtestReferred\":false,\"initialReview\":false,\"active\":true,\"deleted\":false},\"createdBy\":296,\"updatedBy\":296,\"patientTrackId\":520494}";

    }

    public static String getSpiceUserJsonMessageString() {
        return "{\"type\":\"User_Data\",\"patientTrackId\":520494}";

    }

    public static String getSpicePatientJsonMessageString() {
        return "{\"type\":\"Patient_Edit\",\"patientDTO\":{\"id\":103456781,\"createdBy\":858,\"updatedBy\":858,\"createdAt\":\"2023-12-27T06:30:31+00:00\",\"updatedAt\":\"2023-12-27T06:30:31+00:00\",\"tenantId\":107,\"identityType\":\"nationalId\",\"identityValue\":\"TEST@17\",\"firstName\":\"TEST\",\"middleName\":\"TEST\",\"lastName\":\"T\",\"gender\":\"Female\",\"dateOfBirth\":-354173400000,\"age\":65,\"isPregnant\":true,\"phoneNumber\":\"000000000\",\"phoneNumberCategory\":\"Personal\",\"countryId\":4,\"countyId\":7,\"subCountyId\":480,\"siteId\":139,\"landmark\":\"test\",\"occupation\":\"Self Employed\",\"levelOfEducation\":\"College/University Completed\",\"insuranceStatus\":false,\"insuranceType\":null,\"insuranceId\":null,\"otherInsurance\":null,\"isSupportGroup\":null,\"supportGroup\":null,\"isRegularSmoker\":false,\"programId\":12,\"initial\":\"E\",\"otherIdType\":null,\"languages\":null,\"ethnicity\":null,\"idType\":null,\"otherLanguages\":null,\"erVisitReason\":null,\"lote\":null,\"homeMedicalDevices\":null,\"erVisitFrequency\":null,\"emrNumber\":null,\"isErVisitHistory\":null,\"zipCode\":null,\"virtualId\":1088607,\"qrCode\":null,\"active\":true,\"deleted\":false},\"createdBy\":858,\"updatedBy\":858,\"patientTracker\":{\"id\":100017,\"createdBy\":858,\"updatedBy\":858,\"createdAt\":\"2022-01-26T09:09:43+00:00\",\"updatedAt\":\"2023-03-29T10:02:31+00:00\",\"tenantId\":34,\"identityType\":null,\"identityValue\":null,\"nationalId\":\"CHAREFFA5614\",\"firstName\":\"CHARLOTTE\",\"lastName\":\"EFFA\",\"dateOfBirth\":null,\"age\":59,\"gender\":\"Female\",\"phoneNumber\":\"200665614\",\"height\":170.0,\"weight\":76.3,\"bmi\":26.4,\"isRegularSmoker\":false,\"countryId\":1,\"siteId\":139,\"operatingUnitId\":2,\"accountId\":2,\"programId\":1030470,\"avgSystolic\":128,\"avgDiastolic\":80,\"avgPulse\":null,\"glucoseUnit\":\"mmol/L\",\"glucoseType\":\"rbs\",\"glucoseValue\":6.5,\"cvdRiskLevel\":\"Low risk\",\"cvdRiskScore\":6,\"screeningLogId\":null,\"nextMedicalReviewDate\":\"2022-03-26T14:39:43+05:30\",\"nextBpAssessmentDate\":\"2023-04-05T15:32:31+05:30\",\"nextBgAssessmentDate\":null,\"patientId\":51,\"patientStatus\":\"ENROLLED\",\"isObservation\":false,\"isScreening\":false,\"screeningReferral\":false,\"enrollmentAt\":\"2022-01-26T14:39:43+05:30\",\"lastReviewDate\":null,\"lastAssessmentDate\":\"2023-03-29T15:32:31+05:30\",\"isConfirmDiagnosis\":true,\"diagnosisComments\":null,\"confirmDiagnosis\":[\"Hypertension\",\"Diabetes Mellitus Type 2\"],\"provisionalDiagnosis\":[\"HTN\",\"DM\"],\"phq4Score\":null,\"phq4RiskLevel\":null,\"phq4FirstScore\":null,\"phq4SecondScore\":null,\"phq9Score\":null,\"phq9RiskLevel\":null,\"gad7Score\":null,\"gad7RiskLevel\":null,\"riskLevel\":\"Low\",\"isPregnant\":false,\"lastMedicationPrescribedDate\":null,\"isHtnDiagnosis\":true,\"isDiabetesDiagnosis\":true,\"lastLabtestReferredDate\":null,\"totalCount\":0,\"deleteReason\":null,\"deleteOtherReason\":null,\"qrCode\":null,\"village\":null,\"suicidalIdeation\":null,\"cageAid\":null,\"isPregnancyRisk\":null,\"labtestReferred\":false,\"medicationPrescribed\":false,\"redRiskPatient\":false,\"psychologicalAssessment\":false,\"initialReview\":false,\"active\":true,\"deleted\":false}}";


    }

    public static String getSpiceInvalidJsonMessageString() {
        return "{\"type\":\"Invalid_data\",\"patientTrackId\":520494}";

    }

    public static PatientDTO getPatientDTO() {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setFirstName("Jane");
        patientDTO.setInitial("Initial");
        patientDTO.setLastName("Doe");
        patientDTO.setMiddleName("Middle Name");
        patientDTO.setPhoneNumber("6625550144");
        patientDTO.setActive(true);
        patientDTO.setClientRegistryNumber("42");
        patientDTO.setGender("Non-Binary");
        patientDTO.setIdentityValue("42");
        patientDTO.setDateOfBirth(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        return patientDTO;
    }
    public static PatientDTO.PatientDTOBuilder getPatientDTOBuilder() {
        return PatientDTO.builder()
                .BMI(10.0d)
                .age(1)
                .clientRegistryNumber("42")
                .countryId(1L)
                .countyId(1L)
                .createdAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .createdBy(1L).dateOfBirth(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
                .emrNumber(1L)
                .erVisitFrequency(1)
                .erVisitReason("Just cause")
                .ethnicity("Ethnicity")
                .firstName("Jane")
                .gender("Gender")
                .height(10.0d)
                .homeMedicalDevices("Home Medical Devices")
                .id(1L)
                .idType("Id Type")
                .identityType("Identity Type")
                .identityValue("42")
                .initial("Initial")
                .insuranceId("42")
                .insuranceStatus(true)
                .insuranceType("Insurance Type")
                .landmark("Landmark")
                .languages("en")
                .lastName("Doe")
                .levelOfEducation("Level Of Education")
                .lote(true)
                .middleName("Middle Name")
                .occupation("Occupation")
                .otherIdType("Other Id Type")
                .otherInsurance("Other Insurance")
                .otherLanguages("en")
                .phoneNumber("6625550144")
                .phoneNumberCategory("6625550144")
                .programId(1L)
                .qrCode("Qr Code")
                .siteId(1L)
                .subCountyId(1L)
                .supportGroup("Support Group")
                .tenantId(1L);
    }

    public static SpiceFhirMapping getSpiceFhirMapping() {
        SpiceFhirMapping spiceFhirMapping = new SpiceFhirMapping();
        spiceFhirMapping.setFhirId(1);
        spiceFhirMapping.setFhirResourceType("Fhir Resource Type");
        spiceFhirMapping.setId(1L);
        spiceFhirMapping.setReason("Just cause");
        spiceFhirMapping.setSpiceId(1);
        spiceFhirMapping.setSpiceModule("Spice Module");
        spiceFhirMapping.setSpiceModuleId(1L);
        spiceFhirMapping.setStatus("Status");
        return spiceFhirMapping;
    }

    public static BpLogDTO getBpLog() {
        BpLogDTO createBPLog = new BpLogDTO();
        createBPLog.setIsRegularSmoker(true);
        createBPLog.setBmi(10.0d);
        createBPLog.setAvgDiastolic(1);
        createBPLog.setAvgSystolic(1);
        createBPLog.setHeight(10.0d);
        createBPLog.setWeight(10.0d);
        createBPLog.setAvgSystolic(1);
        return createBPLog;
    }
}


package com.mdtlabs.fhir.commonservice.common.utils;

import java.text.MessageFormat;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.time.Instant.now;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mockStatic;
import org.modelmapper.ModelMapper;
import org.springframework.core.annotation.AnnotationUtils;

import com.mdtlabs.fhir.commonservice.common.ErrorMessage;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.constants.TestConstants;
import com.mdtlabs.fhir.commonservice.common.model.dto.BpLogDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirAssessmentRequestDto;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirEnrollmentRequestDto;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirOrganizationRequestDto;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirUserDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.GlucoseLogDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.PatientDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.PractitionerDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.PractitionerRoleDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.UserDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.UserProfileDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.UserRequestDTO;
import com.mdtlabs.fhir.commonservice.common.model.entity.ApiKeyManager;
import com.mdtlabs.fhir.commonservice.common.model.entity.Country;
import com.mdtlabs.fhir.commonservice.common.model.entity.Organization;
import com.mdtlabs.fhir.commonservice.common.model.entity.Role;
import com.mdtlabs.fhir.commonservice.common.model.entity.SpiceFhirMapping;
import com.mdtlabs.fhir.commonservice.common.model.entity.Timezone;
import com.mdtlabs.fhir.commonservice.common.model.entity.User;
import com.mdtlabs.fhir.commonservice.common.model.entity.UserToken;

/**
 * To define the common Data needed for Unit test Cases used all over the application.
 * <p>
 * Author: Hemavardhini Jothi
 * Created on February 27, 2024
 */

public class TestDataProvider {

    private static ModelMapper mapper = new ModelMapper();
    public static ModelMapper modelMapper = new ModelMapper();
    private static MockedStatic<CommonUtil> commonUtil;
    private static MockedStatic<UserContextHolder> userContextHolder;
    private static MockedStatic<MessageFormat> messageFormat;
    private static MockedStatic<AnnotationUtils> annotationUtils;
    public static Country getCountry(){

        Country country = new Country();
        country.setAbbreviation(TestConstants.ABBREVIATION);
        country.setActive(Boolean.TRUE);
        country.setCode(TestConstants.ONE_LONG);
        country.setCreatedAt(Date.from(now().atZone(ZoneOffset.UTC).toInstant()));
        country.setDeleted(Boolean.TRUE);
        country.setId(TestConstants.ONE_LONG);
        country.setName(TestConstants.NAME);
        country.setUpdatedAt(Date.from(now().atZone(ZoneOffset.UTC).toInstant()));
        return country;

    }
    public static Timezone getTimezone() {

        Timezone timezone = new Timezone();
        timezone.setAbbreviation(TestConstants.ABBREVIATION);
        timezone.setActive(Boolean.TRUE);
        timezone.setCreatedAt(Date.from(now().atZone(ZoneOffset.UTC).toInstant()));
        timezone.setDeleted(Boolean.TRUE);
        timezone.setDescription(TestConstants.DESCRIPTION);
        timezone.setId(TestConstants.ONE_LONG);
        timezone.setOffset(TestConstants.OFFSET);
        timezone.setUpdatedAt(Date.from(now().atZone(ZoneOffset.UTC).toInstant()));
        return timezone;

    }

    public static User getUser(){

        User user = new User();
        user.setId(TestConstants.ONE_LONG);
        user.setUsername(TestConstants.NAME);
        user.setActive(Boolean.TRUE);
        user.setAddress(TestConstants.ADDRESS);
        user.setCompanyEmail(TestConstants.EMAIL);
        user.setCompanyName(TestConstants.NAME);
        user.setCountryCode(TestConstants.COUNTRY_CODE);
        user.setCreatedAt(Date.from(now().atZone(ZoneOffset.UTC).toInstant()));
        user.setDeleted(Boolean.TRUE);
        user.setFirstName(TestConstants.NAME);
        user.setForgetPassword(TestConstants.PASSWORD);
        user.setForgetPasswordCount(TestConstants.ONE);
        user.setId(TestConstants.ONE_LONG);
        user.setPassword(TestConstants.PASSWORD);
        user.setMiddleName(Constants.MIDDLE_NAME);
        user.setLastname(Constants.LAST_NAME);
        user.setPhone(Constants.PHONE);
        user.setWebsite(Constants.WEBSITE);
        user.setRoles(new HashSet<>());
        user.setUpdatedAt(Date.from(now().atZone(ZoneOffset.UTC).toInstant()));
        return user;

    }

    public static ApiKeyManager getApiKey() {
        ApiKeyManager apiKeyManager = new ApiKeyManager();
        apiKeyManager.setAccessKeyId(TestConstants.ACCESS_ID);
        apiKeyManager.setActive(Boolean.TRUE);
        apiKeyManager.setCreatedAt(Date.from(now().atZone(ZoneOffset.UTC).toInstant()));
        apiKeyManager.setDeleted(Boolean.TRUE);
        apiKeyManager.setId(TestConstants.ONE_LONG);
        apiKeyManager.setSecretAccessKey(TestConstants.ACCESS_KEY);
        apiKeyManager.setUpdatedAt(Date.from(now().atZone(ZoneOffset.UTC).toInstant()));
        return apiKeyManager;
    }

    public static Role getRole(){

        Role role = new Role();
        role.setActive(Boolean.TRUE);
        role.setAuthority(TestConstants.EMAIL);
        role.setCreatedAt(Date.from(now().atZone(ZoneOffset.UTC).toInstant()));
        role.setDeleted(Boolean.TRUE);
        role.setId(TestConstants.ONE_LONG);
        role.setLevel(TestConstants.ONE);
        role.setName(TestConstants.NAME);
        role.setUpdatedAt(Date.from(now().atZone(ZoneOffset.UTC).toInstant()));
        return role;

    }

    public static List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(getUser());
        return users;
    }
    public static UserDTO getUserDTO() {
        UserDTO user = new UserDTO();
        return mapper.map(getUser(), UserDTO.class);
    }
    public static UserRequestDTO getUserRequestDTO() {
        UserRequestDTO user = new UserRequestDTO();
        user.setFirstName(Constants.FIRST_NAME);
        user.setLastname(Constants.LAST_NAME);
        user.setCompanyEmail(Constants.COMPANY_EMAIL);
        user.setCountryCode(Constants.COUNTRY_CODE);
        user.setAddress(Constants.ADDRESS);
        user.setCompanyName(Constants.COMPANY_NAME);
        user.setPassword(Constants.PASSWORD);
        user.setUsername(Constants.USERNAME);
        user.setPhone(Constants.PHONE);
        return user;
    }

    public static UserProfileDTO getUserProfileDTO() {
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        Set<Role> role = new HashSet<>();
        role.add(TestDataProvider.getRole());
        Set<Organization> organizations = new HashSet<>();
        organizations.add(TestDataProvider.getOrganization());
        userProfileDTO.setId(TestConstants.ONE_LONG);
        userProfileDTO.setCountry(TestDataProvider.getCountry());
        userProfileDTO.setGender(TestConstants.GENDER);
        userProfileDTO.setRoles(role);
        userProfileDTO.setFirstName(Constants.FIRST_NAME);
        userProfileDTO.setCountryCode(Constants.COUNTRY_CODE);
        userProfileDTO.setOrganizations(organizations);
        userProfileDTO.setLastName(Constants.LAST_NAME);
        userProfileDTO.setUsername(Constants.USERNAME);
        userProfileDTO.setTimezone(TestDataProvider.getTimezone());
        return userProfileDTO;
    }

    public static Organization getOrganization() {
        Organization organization = new Organization();
        organization.setFormDataId(TestConstants.FORMDATAID);
        organization.setFormName(TestConstants.FORMNAME);
        organization.setName(TestConstants.NAME);
        organization.setParentOrganizationId(1L);
        return organization;
    }

    public static ApiKeyManager getApiKeyManager() {
        ApiKeyManager apiKeyManager = new ApiKeyManager();
        apiKeyManager.setUser(TestDataProvider.getUser());
        apiKeyManager.setAccessKeyId(Constants.ACCESS_KEY_ID_PARAM);
        apiKeyManager.setSecretAccessKey(Constants.SECRET_ACCESS_KEY_PARAM);
        return apiKeyManager;
    }

    public static PractitionerDTO getPractitionerDTO() {
        PractitionerDTO practitionerDTO = new PractitionerDTO();
        practitionerDTO.setAddress("42 main road");
        practitionerDTO.setGender(TestConstants.GENDER);
        practitionerDTO.setPassword(Constants.PASSWORD);
        practitionerDTO.setLastName(Constants.LAST_NAME);
        practitionerDTO.setUsername(Constants.USERNAME);
        practitionerDTO.setId(TestConstants.ONE_LONG);
        practitionerDTO.setFirstName(Constants.FIRST_NAME);
        practitionerDTO.setCountryCode(Constants.COUNTRY_CODE);
        return practitionerDTO;
    }
    public static FhirUserDTO getFhirUserRequestDTO() {
        FhirUserDTO fhirUserRequestDto = new FhirUserDTO();
        fhirUserRequestDto.setId(1L);
        fhirUserRequestDto.setAddress("42 main road");
        fhirUserRequestDto.setAuthorization(Constants.AUTHORIZATION);
        fhirUserRequestDto.setGender(TestConstants.GENDER);
        fhirUserRequestDto.setPassword(Constants.PASSWORD);
        fhirUserRequestDto.setLastName(Constants.LAST_NAME);
        fhirUserRequestDto.setUsername(Constants.USERNAME);
        return fhirUserRequestDto;
    }


    public static PractitionerRoleDTO getPractitionerRoleDTO() {
        PractitionerRoleDTO practitionerRoleDTO = new PractitionerRoleDTO();
        practitionerRoleDTO.setId(TestConstants.ONE_LONG);
        practitionerRoleDTO.setAuthority(Constants.AUTHORIZATION);
        practitionerRoleDTO.setName(TestConstants.NAME);
        return practitionerRoleDTO;
    }

    public static UserToken getUserToken() {
        UserToken userToken = new UserToken();
        userToken.setAuthToken(Constants.TOKEN);
        userToken.setUserId(Constants.LONG_ONE);
        userToken.setLastSessionTime(new Date());
        return userToken;

    }

    public static ErrorMessage getErrorMessage() {
        return ErrorMessage.builder()
                .dateTime(1L)
                .errorCode(-1)
                .exception("Exception")
                .message(TestConstants.EXCEPTION_MESSAGE)
                .status(true)
                .build();
    }

    public static void init() {
        commonUtil = mockStatic(CommonUtil.class);
        userContextHolder = mockStatic(UserContextHolder.class);
        messageFormat = mockStatic(MessageFormat.class);
        annotationUtils = mockStatic(AnnotationUtils.class);
    }

    public static void getStaticMock() {
        UserDTO userDTO = TestDataProvider.getUserDTO();
        userDTO.setId(TestConstants.ONE_LONG);
        userDTO.setIsSuperUser(Boolean.FALSE);
        commonUtil.when(CommonUtil::getAuthToken).thenReturn("BearerTest");
        userContextHolder.when(UserContextHolder::getUserDto).thenReturn(userDTO);
    }
    public static void cleanUp() {
        commonUtil.close();
        userContextHolder.close();
        messageFormat.close();
        annotationUtils.close();
    }

    public static void getMessageValidatorMock() {
        messageFormat.when(() -> MessageFormat.format("Invalid token.", TestConstants.ARGUMENT, TestConstants.MESSAGE)).thenReturn("message");
        messageFormat.when(() -> MessageFormat.format("Invalid token.", TestConstants.ARGUMENT)).thenReturn("message");
    }
    public static PatientDTO getPatientDTO() {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(TestConstants.ONE_LONG);
        patientDTO.setBMI(10.5d);
        patientDTO.setAge(TestConstants.TEN);
        patientDTO.setCountyId(TestConstants.ONE_LONG);
        patientDTO.setActive(Boolean.TRUE);
        patientDTO.setGender(TestConstants.GENDER);
        patientDTO.setCountryId(TestConstants.ONE_LONG);
        return patientDTO;
    }
    public static GlucoseLogDTO getGlucoseLogDTO() {
        GlucoseLogDTO glucoseLog = new GlucoseLogDTO();
        glucoseLog.setId(TestConstants.ONE_LONG);
        glucoseLog.setGlucoseType("Fasting");
        glucoseLog.setGlucoseValue(90.5);
        return glucoseLog;
    }
    public static FhirEnrollmentRequestDto getFhirEnrollmentRequestDTO() {
        FhirEnrollmentRequestDto fhirEnrollmentRequestDto = new FhirEnrollmentRequestDto();
        fhirEnrollmentRequestDto.setPatient(getPatientDTO());
        fhirEnrollmentRequestDto.setGlucoseLog(getGlucoseLogDTO());
        fhirEnrollmentRequestDto.setPatientTrackId(TestConstants.ONE_LONG);
        fhirEnrollmentRequestDto.setBpLog(getBpLogDTO());
        return fhirEnrollmentRequestDto;
    }

    public static BpLogDTO getBpLogDTO() {
        BpLogDTO bpLogDTO = new BpLogDTO();
        bpLogDTO.setId(1L);
        bpLogDTO.setAvgSystolic(120);
        bpLogDTO.setAvgDiastolic(80);
        bpLogDTO.setAvgPulse(70);
        return bpLogDTO;
    }

    public static SpiceFhirMapping getSpiceFhirMapping() {
        SpiceFhirMapping spiceFhirMapping = new SpiceFhirMapping();
        spiceFhirMapping.setSpiceId(Math.toIntExact(TestConstants.ONE_LONG));
        spiceFhirMapping.setStatus(Constants.INITIAL);
        spiceFhirMapping.setReason(Constants.DATA_PROCESSED_TO_FHIR_ADAPTER);
        spiceFhirMapping.setSpiceModule(Constants.GLUCOSE_LOG);
        spiceFhirMapping.setSpiceModuleId(TestConstants.ONE_LONG);
        return spiceFhirMapping;
    }

    public static FhirAssessmentRequestDto getFhirAssessmentRequestDto() {
        FhirAssessmentRequestDto fhirAssessmentRequestDto = new FhirAssessmentRequestDto();
        fhirAssessmentRequestDto.setGlucoseLog(getGlucoseLogDTO());
        fhirAssessmentRequestDto.setBpLog(getBpLogDTO());
        fhirAssessmentRequestDto.setPatientTrackId(TestConstants.ONE_LONG);
        return fhirAssessmentRequestDto;
    }
    
    public static final FhirUserDTO getFhirUserDTO() {
        FhirUserDTO fhirUserDTO = new FhirUserDTO();
        fhirUserDTO.setId(TestConstants.ONE_LONG);
        fhirUserDTO.setAddress(TestConstants.ADDRESS);
        fhirUserDTO.setFirstName(TestConstants.NAME);
        return fhirUserDTO;
    }

    public static FhirOrganizationRequestDto getFhirOrganizationRequestDTO() {
        FhirOrganizationRequestDto requestDto = new FhirOrganizationRequestDto();
        requestDto.setActive(true);
        requestDto.setCity(Constants.NAME);
        requestDto.setCountryId(1l);
        requestDto.setCountyId(1l);
        requestDto.setAddress1(Constants.ADDRESS);
        requestDto.setName(Constants.NAME);
        requestDto.setCountryName(Constants.COUNTRY);
        requestDto.setAddressType(Constants.ADDRESS);
        requestDto.setAddressUse(Constants.ADDRESS);
        return requestDto;
    }

    public static org.hl7.fhir.r4.model.Organization getFhirOrganization() {
        org.hl7.fhir.r4.model.Organization organization = new org.hl7.fhir.r4.model.Organization();
        organization.setId("1");
        organization.setActive(true);
        organization.setName("Organization");
        organization.setId("1");
        return organization;

    }
}

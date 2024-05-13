package com.mdtlabs.fhir.commonservice.common.constants;

import java.util.List;

/**
 * <p>
 * Class containing constants used throughout the FHIR Common Service application.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
public class Constants {
    public static final String PACKAGE_FHIR_SERVICE = "com.mdtlabs.fhir";
    public static final String PACKAGE_CORE_PLATFORM = "com.mdtlabs.fhir";
    public static final String PACKAGE_FHIR_PLATFORM = "com.mdtlabs.fhir";
    public static final String MESSAGE_RECEIVED = "MESSAGE_RECEIVED";
    public static final String MESSAGE_SUCCESS = "MESSAGE_SUCCESS";
    public static final String MESSAGE_FAILURE = "MESSAGE_FAILURE";
    public static final String MESSAGE_DUPLICATE = "MESSAGE_DUPLICATE";
    public static final String TIMESTAMP = "TIMESTAMP";
    public static final Long LONG_ONE = 1L;
    public static final boolean TRUE = true;
    public static final boolean FALSE = false;
    public static final String DUPLICATE_NOT_SENT_TO_ADAPTER = "This is a duplicate message and it is not sent to fhir-adapter";
    public static final String PASSWORD = "password";
    public static final String FHIR_SALT_NAME = "Fh1R@dmIN";
    public static final String PGP_DECRYPT = "public.pgp_sym_decrypt(password::bytea, ";
    public static final String PGP_ENCRYPT = "public.pgp_sym_encrypt(?, ";
    public static final String BYTEA = "bytea";
    public static final String LOGGER = "Logger";
    public static final String USERS_BASE_URI = "/users";
    public static final String SUCCESS = "SUCCESS";
    public static final String SUCCESS_PROPERTIES_PATH = "/success_messages.properties";
    public static final String ERROR_PROPERTIES_PATH = "/error_messages.properties";
    public static final String JSON_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String ERROR = "ERROR";
    public static final String USER_BY_ID_URI = "/{id}";
    public static final String USER_BY_USERNAME_URI = "/username/{username}";
    public static final String UPDATE_USER_URI = "/update";
    public static final String RESET_PASSWORD_URI = "/reset-password";
    public static final String DELETE_USER_URI = "/delete";
    public static final String FORGOT_PASSWORD_URI = "/forgot-password";
    public static final String USERNAME_URI = "/username";
    public static final String PROFILE_URI = "/profile";
    public static final String ALL_PAGE_NUMBER_URI = "/all/{pageNumber}";
    public static final Boolean BOOLEAN_TRUE = Boolean.TRUE;
    public static final Boolean BOOLEAN_FALSE = Boolean.FALSE;
    public static final String EMPTY = "";
    public static final List<Object> NO_DATA_LIST = List.of();
    public static final String PAGE_NUMBER = "pageNumber";
    public static final String IS_PASSWORD_SET = "isPasswordSet";
    public static final String USERNAME = "username";
    public static final String FIRST_NAME = "firstName";
    public static final String MIDDLE_NAME = "middleName";
    public static final String LAST_NAME = "lastname";
    public static final String COMPANY_NAME = "companyName";
    public static final String COMPANY_EMAIL = "companyEmail";
    public static final String COUNTRY_CODE = "countryCode";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";
    public static final String WEBSITE = "website";
    public static final String COUNTRY = "country";
    public static final String TIMEZONE = "timezone";
    public static final String ROLES = "roles";
    public static final String INVALID_API = "The provided API key credentials are invalid.";
    public static final String API_KEY_MANAGER_BASE_URI = "/api-key-managers";
    public static final String API_KEY_BY_USER_ID_URI = "/user";
    public static final String DELETE_API_KEY_URI = "/delete";
    public static final long EXPIRY_MINUTES = 30;
    public static final String USER_ID_PARAM = "userId";
    public static final String VALIDATE_ENDPOINT = "/validate";
    public static final String ACCESS_KEY_ID_PARAM = "accessKeyId";
    public static final String SECRET_ACCESS_KEY_PARAM = "secretAccessKey";
    public static final String DATE_FORMAT_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ssxxx";
    public static final String AES_KEY_TOKEN = "Fh1R@dmIN";
    public static final long TWENTY_FOUR = 24;
    public static final String ISSUER = "FHIRApplication";
    public static final Integer INT_ONE = 1;
    public static final String VALID_API = "The provided API key credentials are valid.";
    // FHIR
    public static final String MESSAGE_SENT_TO_HAPI_SERVER_SUCCESSFULLY = "Message Successfully saved in HAPI-FHIR";
    public static final String PROCESSED = "PROCESSED";
    public static final String DATA_PROCESSED_TO_FHIR_ADAPTER = "Data Successfully Processed to FHIR ADAPTER";
    public static final String DATA_PROCESSED_TO_FHIR_SERVER = "Data Successfully Processed to FHIR server";
    public static final String FAILED_STATUS_UPDATED = "Failed Status Successfully updated";
    public static final String FBS = "fbs";
    public static final String RBS = "rbs";
    public static final String PATIENT = "Patient";
    public static final String BP_LOG = "Bp_Log";
    public static final String GLUCOSE_LOG = "Glucose_Log";
    public static final String INITIAL = "INITIAL";
    public static final String ENROLLMENT_DATA = "Enrollment_Data";
    public static final String ASSESSMENT_DATA = "Assessment_Data";
    public static final String USER_DATA = "User_Data";
    public static final String USER = "user";
    public static final String ENTRY = "entry";
    public static final String RESPONSE = "response";
    public static final String LOCATION = "location";
    //Symbols & Numbers
    public static final int ZERO = 0;
    public static final String SINGLE_QUOTE = "'";
    public static final String CLOSE_BRACKET = ")";
    public static final String SPACE = " ";
    public static final String OPEN_SQUARE_BRACKET = "[";
    public static final String CLOSE_SQUARE_BRACKET = "]";
    public static final String HYPHEN = "-";
    public static final String FORWARD_SLASH = "/";
    public static final String MESSAGE = "message";
    public static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    public static final String CONTENT_TEXT_TYPE = "text/x-json;charset=UTF-8";
    public static final String CACHE_HEADER_NAME = "Cache-Control";
    public static final String CACHE_HEADER_VALUE = "no-cache";
    public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String ANONYMOUS_USER = "anonymousUser";
    public static final String ROLE_SUPER_USER = "SUPER_USER";
    public static final String TOKEN_ISSUER = "User";
    public static final String AUTH_TOKEN_SUBJECT = "Auth_Token";
    public static final long AUTH_TOKEN_EXPIRY_MINUTES = 120;
    public static final String RSA = "RSA";
    public static final String ASTERISK_SYMBOL = "*";
    public static final String FHIR_ADAPTER_SERVICE = "fhir-adapter-service";
    public static final String OTHER = "other";
    public static final String NON_BINARY = "Non-Binary";
    public static final String ADAPTER_SERVICE_PONG = "Adapter Service: pong!";
    public static final String FHIR_ADMIN = "FHIR_Admin";
    public static final String PING = "/ping";
    public static final String PATIENT_DATA_SAVED_IN_DATABASE = "Patient details successfully saved in HAPI FHIR " +
            "Database: {}";
    public static final String JSON_BUNDLE_BEFORE_HAPI_JPA = "Bundle Json before sending it to HAPI JPA Server: {}";
    public static final String JSON_BEFORE_HAPI_JPA = " Json before sending it to HAPI JPA Server: {}";
    public static final String USER_PRACTITIONER_MAPPING_SAVED_SUCCESSFULLY = "User & Practitioner Id's have been " +
            "saved successfully in USER_PRACTITIONER_MAPPING Table";
    public static final String PRACTITIONER_ROLE_LOG = "Practitioner Role: {}";
    public static final String CONVERTED_SPICE_ENROLLED_PATIENT_TO_FHIR_PATIENT = "SPICE Enrolled Patient converted " +
            "to FHIR Patient";
    public static final String CONVERTED_SPICE_BP_TO_FHIR_OBSERVATION = "SPICE BP Log converted to FHIR Observation";
    public static final String CONVERTED_SPICE_BG_TO_FHIR_OBSERVATION = "SPICE Glucose Log converted to FHIR " +
            "Observation";
    public static final String CONVERTED_ENROLLMENT_MODULE_TO_FHIR_BUNDLE = "Enrollment Details Successfully " +
            "converted into FHIR bundle: {}";
    public static final String CONVERTED_ASSESSMENT_MODULE_TO_FHIR_BUNDLE = "Assessment Details Successfully " +
            "converted into FHIR bundle: {}";
    public static final String MESSAGE_SAVED_IN_FHIR_MAPPING_TABLE = "SPICE Message object saved in FHIR_MAPPING Table";
    public static final String ASSESSMENT_DETAILS_SAVED_IN_HAPI_SERVER = "Assessment Data saved in HAPI FHIR " +
            "Database: {}";
    public static final String REQUEST_SAVED_IN_FHIR_MAPPING_TABLE = " request object successfully saved in " +
            "FHIR_MAPPING Table";
    public static final String FHIR_PATIENT_ID = "Fhir Patient Id: ";
    public static final String POST_CALL_TO_FHIR_ADAPTER = "POST call to FHIR Adapter: {}";
    public static final String FHIR_ADAPTER_RESPONSE = "Fhir Adapter Response: {}";
    public static final String RECEIVED_MESSAGE_FROM_QUEUE = "FHIR QUEUE: Received message \n{} ";
    public static final String NO_SITE_MAPPINGS_FOUND = "No site mappings found in FHIR server";
    public static final String USER_SUCCESSFULLY_CONVERTED = "SPICE User successfully converted to FHIR Practitioner";
    public static final String PRACTITIONER_CONVERTED_TO_USER = "FHIR Practitioner successfully converted to " +
            "PractitionerDTO";
    public static final String USER_ROLE_CONVERTED_TO_PRACTITIONER = "SPICE User Role successfully converted to FHIR " +
            "PractitionerRole";
    public static final String PRACTITIONER_ROLE_CONVERTED_TO_USER_ROLE = "FHIR PractitionerRole successfully " +
            "converted to PractitionerRoleDTO";

    public static final String CREATE_OBSERVATION_URI = "/create-observation";
    public static final String ASSESSMENT_URI = "/assessment";
    public static final String ENROLLMENT_URI = "/enrollment";
    public static final String CREATE_URI = "/create";
    public static final String MESSAGE_URI = "/message";
    public static final String RECEIVE_MESSAGE_URI = "/receive-message";
    public static final String USER_URI = "/user";
    public static final String ROLE_CREATE_URI = "/role/create";
    public static final String PATIENT_EDIT = "Patient_Edit";
    public static final String MESSAGE_UPDATED_SUCCESSFULLY = "Message Updated successfully";
    public static final String GENERATE_URI = "/generate";
    public static final String PROCESS_MESSAGE_REQUEST_FROM_LISTENER = "/message/receive-message";
    public static final String USER_TOKEN_SERVICE = "UserTokenService";
    public static final String DEDUPLICATION_ID = "deduplicationId";
    public static final String BODY = "body";
    public static final String NAME = "name";
    public static final String TOKEN = "token";
    public static final String SITE_DATA = "Site_Data";
    public static final String POSTAL = "POSTAL";
    public static final String PHYSICAL = "PHYSICAL";
    public static final String PHYSICAL_ADDRESS = "PHYSICAL ADDRESS";
    public static final String UNKNOWN_ADDRESS_TYPE_CODE = "Unknown AddressType code: ";
    public static final String OBSOLETE = "OBSOLETE";
    public static final String TEMPORARY = "TEMPORARY";
    public static final String HOME = "HOME";
    public static final String WORK = "WORK";
    public static final String UNKNOWN_ADDRESS_USE_CODE = "Unknown AddressUse code: ";
    public static final String IDENTIFIER_URL = "http://mdtlabs.com/";
    public static final String ORGANIZATION = "Organization";
    public static final String LOINC_SYSTEM_URL = "http://loinc.org";
    public static final String REGEX = "\\|";
    public static final String KENYA = "Kenya";
    public static final String UNKNOWN = "Unknown";
    public static final int KENYA_ID = 1;
    public static final String USER_MIGRATION_URI = "/migration/user-migrate";
    public static final String SITE_MIGRATION_URI = "/migration/site-migrate";

    /**
     * <p>
     * Private constructor to prevent instantiation.
     * </p>
     * <p>
     * Author: Akash Gopinath
     * Created on: February 26, 2024
     */
    private Constants() {
    }
}

package com.mdtlabs.fhir.commonservice.common.constants;

/**
 * <p>
 * Class containing constants used throughout the FHIR Common Service application.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
public class ErrorConstants {
    public static final String COUNTRY_NAME_NOT_NULL = "Country name should not be empty";
    public static final String COUNTRY_CODE_NOT_NULL = "Country code should not be empty";
    public static final String RESOLVER_ERROR = "Error message construction using resolver Error Message";
    public static final String MESSAGE_GENERIC = "Failed to process the request for the following reason : ";
    //FHIR
    public static final String SITE_ID_NOT_NULL = "Site Id should not be null";
    public static final String AVG_SYSTOLIC_NOT_NULL = "Average systolic should not be empty";
    public static final String AVG_DIASTOLIC_NOT_NULL = "Average diastolic should not be empty";
    public static final String IS_REGULAR_SMOKER = "IsRegularSmoker should not be empty";
    public static final String BP_LOG_DETAILS_NOT_EMPTY = "BPLog details should not be empty";
    public static final String BP_LOG_DETAILS_MIN_SIZE = "BpLog details should contains minimum 2 values";
    public static final String AGE_NOT_NULL = "Age should not be null";
    public static final String AGE_MIN_VALUE = "Age should be greater than 0";
    public static final String GENDER_NOT_NULL = "Gender should not be null";
    public static final String DUPLICATE_PATIENT = "Duplicate Patient";
    public static final String PATIENT_NOT_ENROLLED_IN_FHIR_LOG = "Patient Not Enrolled in FHIR: {}";
    public static final String PATIENT_NOT_ENROLLED_IN_FHIR = "Patient Not Enrolled in FHIR";
    public static final String ASSESSMENT_DETAILS_NOT_SAVED_IN_FHIR = "Assessment details not saved in FHIR";
    public static final String RESOURCE_VALIDATION_FAILED = "Patient Mandatory Fields are  either entirely null or contain null values";
    public static final String ASSESSMENT_RESOURCE_VALIDATION_FAILED = "BP & BG details both are empty";
    public static final String INVALID_RESOURCE_CATEGORY = "Invalid resource category";
    public static final String USER_MAPPINGS_NOT_FOUND = "No user mappings found in FHIR server: {}";
    public static final String SAME_PASSWORD = "New password cannot be same as old password";
    public static final String ERROR_USERNAME_PASSWORD_BLANK = "No Username and / or Password Provided";
    public static final String ERROR_INVALID_USER = "Invalid credentials";
    public static final String ERROR_INVALID_ROLE = "Invalid Role";
    public static final String ERROR_INVALID_ATTEMPTS = "Account locked due to multiple invalid login attempts.";
    public static final String INFO_USER_NOT_EXIST = "Username does not exist : ";
    public static final String INFO_USER_PASSWORD_NOT_MATCH = "Password doesn't match for the user : ";
    public static final String EXCEPTION_TOKEN_UTILS = "Exception occurred while loading token utils";
    public static final String INVALID_USER_ERROR = "{ \"error\": \"Invalid User\"}";
    public static final String LOGIN_ERROR = "Login Error ";
    public static final String ERROR_JWE_TOKEN = "Error while creating jwe token ";
    public static final String EXCEPTION_DURING_TOKEN_UTIL = "Exception occurred while loading token utils";
    public static final String LINK_EXPIRED = "Link has expired.";
    public static final String PASSWORD_NOT_EXIST = "Password not set for the user";
    public static final String INVALID_API_KEY = "Invalid accessKeyId or secretAccessKey";
    public static final String UNABLE_TO_CONVERT_THE_FHIR_JSON_OBJECT = "Unable to convert the FHIR JSON object: {}";
    public static final String RESOURCE_CATEGORY_NOT_PATIENT_OR_OBSERVATION = "Provided Resource is other than " +
            "Patient or Observation Category";
    public static final String RESOURCE_CATEGORY_NOT_OBSERVATION = "Provided Resource is other than Observation Category";
    public static final String ERROR_LOG = "Error Log: {}";
    public static final String ISSUE_CONVERTING_REQUEST_TO_REQUEST_DTO = "Failed to convert the request object to " +
            "their respective DTO's: {}";
    public static final String FAILED_TO_SAVE_REQUEST = "Failed to save the request in FHIR MAPPING TABLE";
    public static final String BAD_REQUEST = "Bad Request Exception";
    public static final String UNAUTHORIZED = "UnAuthorized Exception";
    public static final String DATA_NOT_FOUND = "Data Not found Exception";
    public static final String DATA_NOT_ACCEPTABLE = "Data Not Acceptable Exception";
    public static final String REQUEST_TIME_OUT = "Request Time Out Exception";
    public static final String DATA_CONFLICT = "Data Conflict Exception";
    public static final String EXCEPTION_MESSAGE = "\"ExceptionMessage{\"";
    public static final String DATE_TIME_FIELD = "dateTime=";
    public static final String STATUS_FIELD = ", status=";
    public static final String ERROR_CODE_FIELD = ", errorCode=";
    public static final String MESSAGE_FIELD = ", message='";
    public static final String EXCEPTION_FIELD = ", exception='";
    public static final String FORWARD_SLASH = "'\''";
    public static final String CLOSING_BRACE = "}";
    public static final String SITE_ID_NULL = "Site Id cannot be null";
    public static final String VALIDATION_FAILED = "Validation failed for resource ";
    public static final String ERRORS = " Errors: {}";

    /**
     * <p>
     * Private constructor to prevent instantiation.
     * </p>
     * <p>
     * Author: Akash Gopinath
     * Created on: February 26, 2024
     */
    private ErrorConstants() {
    }
}
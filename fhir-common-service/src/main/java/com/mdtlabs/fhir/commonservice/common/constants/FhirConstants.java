package com.mdtlabs.fhir.commonservice.common.constants;

public class FhirConstants {
    public static final String IDENTIFIER_URL = "http://mdtlabs.com/";
    public static final String CLIENT_REGISTRY_IDENTIFIER_URL = "https://nhdd.health.go.ke";

    public static final String PATIENT = "Patient";
    public static final String OBSERVATION = "Observation";
    public static final String PRACTITIONER = "Practitioner";
    public static final String ORGANIZATION = "Organization";
    public static final String PRACTITIONER_ROLE = "PractitionerRole";
    public static final String BP_LOG = "Bp_Log";
    public static final String GLUCOSE_LOG = "Glucose_Log";
    public static final String NHDD_SYSTEM_URL = "https://nhdd.health.go.ke/";

    public static final String LOINC_SYSTEM_URL = "http://loinc.org";

    public static final String LABORATORY_SYSTEM_URL = "http://hl7.org/fhir/observation-category";

    public static final String NHDD_SYSTOLIC_BLOOD_PRESSURE_CODE = "13550";
    public static final String NHDD_DIASTOLIC_BLOOD_PRESSURE_CODE = "13547";
    public static final String NHDD_HEIGHT_CODE = "13538";
    public static final String NHDD_WEIGHT_CODE = "13541";
    public static final String NHDD_BMI_CODE = "16755";
    public static final String NHDD_HBA1C_CODE = "29790";
    public static final String NHDD_FBS_CODE = "27296";
    public static final String NHDD_RBS_CODE = "851";
    public static final String NHDD_PREGNANT_STATUS_CODE = "2089";
    public static final String NHDD_SMOKER_CODE = "41058";

    public static final String BLOOD_PRESSURE_CODE = "55284-4";
    public static final String SYSTOLIC_BLOOD_PRESSURE_CODE = "8480-6";
    public static final String DIASTOLIC_BLOOD_PRESSURE_CODE = "8462-4";
    public static final String HEIGHT_CODE = "8302-2";
    public static final String WEIGHT_CODE = "29463-7";
    public static final String BMI_CODE = "39156-5";
    public static final String FBS_LOINC_CODE = "1558-6";
    public static final String RBS_LOINC_CODE = "2345-7";
    public static final String HBA1C_LOINC_CODE = "4548-4";
    public static final String LAST_MEAL_TIME = "12345-6";
    public static final String PREGNANT_STATUS = "82810-3";
    public static final String SMOKING_STATUS = "72166-2";

    public static final String LABORATORY_CODE = "laboratory";

    public static final String DOCTOR = "doctor";

    public static final String BLOOD_PRESSURE_DISPLAY = "Blood pressure systolic and diastolic";
    public static final String SYSTOLIC_BLOOD_PRESSURE_DISPLAY = "Systolic blood pressure";
    public static final String DIASTOLIC_BLOOD_PRESSURE_DISPLAY = "Diastolic blood pressure";
    public static final String HEIGHT_DISPLAY = "Height";
    public static final String WEIGHT_DISPLAY = "Weight";
    public static final String BMI_DISPLAY = "Body Mass Index (BMI)";
    public static final String HBA1C_DISPLAY = "HbA1c";
    public static final String LAST_MEAL_TIME_DISPLAY = "Last Meal Time";
    public static final String PREGNANT_STATUS_DISPLAY = "Pregnant Status";
    public static final String REGULAR_SMOKER_DISPLAY = "Regular Smoker Status";
    public static final String NHDD_PREGNANT_STATUS_DISPLAY = "Currently pregnant";
    public static final String NHDD_REGULAR_SMOKER_DISPLAY = "Smoker";

    public static final String UOM_SYSTEM_URL = "http://unitsofmeasure.org";
    public static final String FHIR_ROLE_URL = "http://example.com/rolesystem";

    public static final String MM_HG_CODE = "mm[Hg]";
    public static final String CM_CODE = "cm";
    public static final String KG_CODE = "kg";
    public static final String KG_PER_M2_CODE = "kg/m^2";

    public static final String SPICE_MODULE = "spiceModule";
    public static final String SPICE_PATIENT_TRACK_ID = "patientTrackId";
    public static final String PROGRESS_STATUS = "progressStatus";
    public static final String FHIR_MODULE = "fhirType";
    public static final String VALIDATION_OR_DUPLICATE_MESSAGE = "errorMessage";
    public static final String BLOOD_GLUCOSE = "Blood Glucose";
    public static final String BLOOD_PRESSURE = "Blood Pressure";
    public static final String PATIENT_FULL_URL = "urn:uuid:patient";
    public static final String BLOOD_PRESSURE_TEXT = "Blood Pressure";
    public static final String BLOOD_GLUCOSE_TEXT = "Blood Glucose";
    public static final String SITE_SPICE_ID = "siteSpiceId";

    private FhirConstants() {
    }
}

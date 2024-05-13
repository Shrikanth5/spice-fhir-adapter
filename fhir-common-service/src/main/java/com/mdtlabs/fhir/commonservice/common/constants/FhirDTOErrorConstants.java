package com.mdtlabs.fhir.commonservice.common.constants;

public class FhirDTOErrorConstants {
    public static final String IS_REGULAR_SMOKER = "IsRegularSmoker should not be empty";
    public static final String GENDER_NOT_NULL = "Gender should not be null";
    public static final String AGE_NOT_NULL = "Age should not be null";
    public static final String AGE_MIN_VALUE = "Age should be greater than 0";
    public static final String SITE_ID_NOT_NULL = "Site Id should not be null";

    private FhirDTOErrorConstants() {
    }

}

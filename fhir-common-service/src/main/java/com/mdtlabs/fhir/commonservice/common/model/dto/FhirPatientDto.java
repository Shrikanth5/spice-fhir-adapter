package com.mdtlabs.fhir.commonservice.common.model.dto;

import com.mdtlabs.fhir.commonservice.common.constants.FhirDTOErrorConstants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <p>
 * This DTO class handling the FhirPatient details
 * </p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FhirPatientDto {
    private Long id;

    private String nationalId;

    private String firstName;

    private String middleName;

    private String lastName;

    @NotEmpty(message = FhirDTOErrorConstants.GENDER_NOT_NULL)
    private String gender;

    private Date dateOfBirth;

    @NotNull(message = FhirDTOErrorConstants.AGE_NOT_NULL)
    @PositiveOrZero(message = FhirDTOErrorConstants.AGE_MIN_VALUE)
    private Integer age;

    private Boolean isPregnant;

    private String phoneNumber;

    private String phoneNumberCategory;

    @NotNull
    private Long countryId;

    @NotNull
    private Long countyId;

    @NotNull
    private Long subCountyId;

    @NotNull(message = FhirDTOErrorConstants.SITE_ID_NOT_NULL)
    private Long siteId;

    private String landmark;

    private String occupation;

    private String levelOfEducation;

    private Boolean insuranceStatus;

    private String insuranceType;

    private String insuranceId;

    private String otherInsurance;

    @NotNull(message = FhirDTOErrorConstants.IS_REGULAR_SMOKER)
    private Boolean isRegularSmoker;

    private Long programId;

    private String initial;

    private Long zipCode;

    private Long virtualId;

    private Boolean isActive;

    private Boolean isDeleted;

    private String languages;

}
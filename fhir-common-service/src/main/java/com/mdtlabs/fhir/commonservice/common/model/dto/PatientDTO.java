package com.mdtlabs.fhir.commonservice.common.model.dto;

import com.mdtlabs.fhir.commonservice.common.constants.ErrorConstants;
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
 * This DTO class handling the patient details
 * </p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PatientDTO {

    private Long id;

    private String identityType;

    private String identityValue;

    private String clientRegistryNumber;


    private String firstName;

    private String middleName;

    private String lastName;

    @NotEmpty(message = ErrorConstants.GENDER_NOT_NULL)
    private String gender;

    private Date dateOfBirth;

    @NotNull(message = ErrorConstants.AGE_NOT_NULL)
    @PositiveOrZero(message = ErrorConstants.AGE_MIN_VALUE)
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

    @NotNull(message = ErrorConstants.SITE_ID_NOT_NULL)
    private Long siteId;

    private String landmark;

    private String occupation;

    private String levelOfEducation;

    private Boolean insuranceStatus;

    private String insuranceType;

    private String insuranceId;

    private String otherInsurance;

    private Boolean isSupportGroup;

    private String supportGroup;

    @NotNull(message = ErrorConstants.IS_REGULAR_SMOKER)
    private Boolean isRegularSmoker;

    private Long programId;

    private String initial;

    private String otherIdType;

    private String languages;

    private String ethnicity;

    private String idType;

    private String otherLanguages;

    private String erVisitReason;

    private Boolean lote;

    private String homeMedicalDevices;

    private Integer erVisitFrequency;

    private Long emrNumber;

    private Boolean isErVisitHistory;

    private Long zipCode;

    private Long virtualId;

    private String qrCode;

    private Long tenantId;
    private boolean isActive;

    private boolean isDeleted;

    private Double height;

    private Double weight;

    private Double BMI;
    private Long createdBy;

    @NotNull
    private Long updatedBy;
    @NotNull
    private Date createdAt;

    @NotNull
    private Date updatedAt;
}

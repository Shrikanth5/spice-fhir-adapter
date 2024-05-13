package com.mdtlabs.fhir.commonservice.common.model.dto;

import com.mdtlabs.fhir.commonservice.common.constants.ErrorConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * This class is an Request DTO class for assessment.
 * </p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
@Validated
public class AssessmentRequestDTO {
    @NotNull
    private String type;

    private Long patientTrackId;

    private String identityType;

    private String identityValue;

    @Valid
    private BpLogDTO bpLog;

    private GlucoseLogDTO glucoseLog;

    private String gender;

    private String firstName;

    private String lastName;

    private Integer age;

    @NotNull(message = ErrorConstants.IS_REGULAR_SMOKER)
    private Boolean isRegularSmoker;

    private Integer cvdRiskScore;

    private String unitMeasurement;

    private String cvdRiskScoreDisplay;

    private String cvdRiskLevel;

    private MentalHealthDTO phq4;

    private Long siteId;

    private Long operatingUnitId;

    private List<Map<String, Object>> customizedWorkflows;

    private List<String> provisionalDiagnosis;

    private String village;

    private Map<String, String> assessmentLocation;

    private MentalHealthDTO phq9;

    private MentalHealthDTO gad7;

    private String qrCode;

    private Date lastLoggedDate;

    @NotNull
    private Long createdBy;

    @NotNull
    private Long updatedBy;

    @NotNull
    private Date createdAt;

    @NotNull
    private Date updatedAt;

}

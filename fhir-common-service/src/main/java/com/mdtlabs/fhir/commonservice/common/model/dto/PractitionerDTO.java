package com.mdtlabs.fhir.commonservice.common.model.dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * This is a DTO class for practitioner entity.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
public class PractitionerDTO {

    private Long id;

    private Long fhirId;

    private Long createdBy;

    private Long updatedBy;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    private boolean isActive;

    private boolean isDeleted;

    private String username;

    private String password;

    private String gender;

    private Set<PractitionerRoleDTO> roles = new HashSet<>();

    private Date blockedDate;

    private String forgetPasswordToken;

    private Date forgetPasswordTime;

    private int forgetPasswordCount;

    private Date invalidLoginTime;

    private int invalidLoginAttempts;

    private Date invalidResetTime;

    private Boolean isPasswordResetEnabled;

    private int passwordResetAttempts;

    private boolean isLicenseAcceptance;

    private Date lastLoggedIn;

    private Date lastLoggedOut;

    private String address;

    private String firstName;

    private String lastName;

    private String middleName;

    private String phoneNumber;

    private Long countryId;

    private Long timezoneId;

    private Boolean isBlocked;

    private String countryCode;

    private long tenantId;

    private Long cultureId;

    public Set<PractitionerRoleDTO> getRoles() {
        if (Objects.isNull(roles)) {
            return Collections.emptySet();
        }
        return roles.stream().map(role -> new ModelMapper().map(role, PractitionerRoleDTO.class)).collect(Collectors.toSet());
    }

}

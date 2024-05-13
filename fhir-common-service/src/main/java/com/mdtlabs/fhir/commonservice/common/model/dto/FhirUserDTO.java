package com.mdtlabs.fhir.commonservice.common.model.dto;

import com.mdtlabs.fhir.commonservice.common.model.entity.Role;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;

/**
 * <p>
 * This DTO class handling the FhirUserRequest details
 * </p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
public class FhirUserDTO {

    private Long id;

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

    private ArrayList<Role> roles;

    private boolean active;

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

    private boolean accountExpired;

    private boolean accountLocked;

    private boolean credentialsExpired;

    private String authorization;

    private long currentDate;

    private String firstName;

    private String lastName;

    private String middleName;

    private String subject;

    private String phoneNumber;

    private Boolean isBlocked;

    private String countryCode;

    private Long deviceInfoId;

    private long tenantId;

    private Boolean isSuperUser = false;

    private Long cultureId;

    private Long siteId;

    private String siteName;

    private String catchmentArea;

    private String communityUnion;

}

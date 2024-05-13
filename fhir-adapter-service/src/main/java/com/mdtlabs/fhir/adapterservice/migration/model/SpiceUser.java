package com.mdtlabs.fhir.adapterservice.migration.model;

import lombok.Data;

import java.util.Date;

/**
 * The type Spice user.
 * Author: Akash Gopinath
 * Created on: April 10, 2024
 */
@Data
public class SpiceUser {
    private Long id;
    private Long createdBy;
    private Long updatedBy;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isActive;
    private boolean isDeleted;
    private String username;
    private String password;
    private String gender;
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
